/*
 * This file is part of ceserver-pcileech by Isabella Flores
 *
 * Copyright © 2021 Isabella Flores
 *
 * It is licensed to you under the terms of the
 * GNU Affero General Public License, Version 3.0.
 * Please see the file LICENSE for more information.
 */

package iflores.ceserver.pcileech;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class MainFrame extends JFrame implements RunningServerListener {

    private static final AtomicReference<RunningServer> _server = new AtomicReference<>();
    private final JPanel _settingsPanel;
    private final JButton _startStopButton;
    private final JTextArea _outputArea;

    public MainFrame(Settings settings) {
        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    RunningServer server = _server.get();
                    if (server != null) {
                        try {
                            server.shutdownNowAndWait();
                        } catch (Throwable t) {
                            t.printStackTrace();
                        }
                    }
                })
        );

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        _settingsPanel = new JPanel(new GridBagLayout());
        _settingsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(_settingsPanel, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.SOUTH);
        _settingsPanel.add(
                new JLabel("MemProcFS.exe Location:"),
                new GridBagConstraintsBuilder()
                        .gridx(0)
                        .anchor(GridBagConstraints.EAST)
        );
        JTextField memprocFsPathTextField = new JTextField(30);
        memprocFsPathTextField.setText(settings.getMemProcFsExePath());
        memprocFsPathTextField.setEditable(false);
        _settingsPanel.add(
                memprocFsPathTextField,
                new GridBagConstraintsBuilder()
                        .gridx(1)
                        .insets(new Insets(0, 5, 0, 5))
                        .weightx(1.0)
                        .fill(GridBagConstraints.HORIZONTAL)
        );
        JButton browseButton = new JButton("Browse...");
        _settingsPanel.add(browseButton, new GridBagConstraintsBuilder().gridx(2));
        _settingsPanel.add(
                new JLabel("Arguments to PCILeech:"),
                new GridBagConstraintsBuilder().gridx(0).anchor(GridBagConstraints.EAST)
        );
        JTextField argsTextField = new JTextField(30);
        argsTextField.setText(settings.getPciLeechArguments());
        _settingsPanel.add(
                argsTextField,
                new GridBagConstraintsBuilder()
                        .gridx(1)
                        .insets(new Insets(0, 5, 0, 5))
                        .weightx(1.0)
                        .fill(GridBagConstraints.HORIZONTAL)
        );
        _startStopButton = new JButton();
        buttonPanel.add(_startStopButton);

        _outputArea = new JTextArea(10, 30);
        _outputArea.setEditable(false);
        _outputArea.setAutoscrolls(true);
        add(new JScrollPane(_outputArea), BorderLayout.CENTER);

        browseButton.addActionListener(e -> {
            File result = openFileDialog_MemProcFsExe();
            if (result != null) {
                settings.setMemProcFsExePath(result.getAbsolutePath());
                memprocFsPathTextField.setText(settings.getMemProcFsExePath());
                settings.save();
            }
        });

        _startStopButton.addActionListener(
                e -> {
                    RunningServer server = _server.get();
                    if (server == null) {
                        _outputArea.setText("");
                        _startStopButton.setText("Stop Server");
                        ProcessBuilder pb = new ProcessBuilder(
                                System.getProperty("java.home") + "\\bin\\java.exe",
                                "-classpath",
                                System.getProperty("java.class.path"),
                                ServerMain.class.getName(),
                                settings.getMemProcFsExePath(),
                                settings.getPciLeechArguments().trim()
                        );
                        pb.redirectInput(ProcessBuilder.Redirect.PIPE);
                        pb.redirectOutput(ProcessBuilder.Redirect.PIPE);
                        pb.redirectErrorStream(true);
                        try {
                            Process p = pb.start();
                            server = new RunningServer(p);
                            _server.set(server);
                            server.addListener(this);
                            server.start();
                            updateServerState();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        server.shutdownNow();
                        _startStopButton.setEnabled(false);
                    }
                }
        );

        argsTextField.getDocument().addDocumentListener(new DocumentChangeListener(() -> {
            settings.setPciLeechArguments(argsTextField.getText());
            settings.save();
        }));

        updateServerState();
        pack();
        setLocationRelativeTo(null);
    }

    private static void enableContainer(Container container, boolean enabled) {
        for (Component component : container.getComponents()) {
            if (component instanceof Container) {
                enableContainer((Container) component, enabled);
            }
            component.setEnabled(enabled);
        }
    }

    private void updateServerState() {
        if (_server.get() == null) {
            enableContainer(_settingsPanel, true);
            _startStopButton.setText("Start Server");
        } else {
            enableContainer(_settingsPanel, false);
            _startStopButton.setText("Stop Server");
        }
    }

    private File openFileDialog_MemProcFsExe() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(
                new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory() || f.getName().equalsIgnoreCase("MemProcFS.exe");
                    }

                    @Override
                    public String getDescription() {
                        return "MemProcFS.exe";
                    }
                }
        );
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        } else {
            return null;
        }
    }

    @Override
    public void charsRead(String chars) {
        _outputArea.append(chars);
    }

    @Override
    public void closed(Integer exitCode) {
        _server.set(null);
        if (!_outputArea.getText().isEmpty() && !_outputArea.getText().endsWith("\n")) {
            _outputArea.append("\n");
        }
        if (exitCode == null) {
            _outputArea.append("*** Server died unexpectedly\n");
        } else if (exitCode == 0) {
            _outputArea.append("*** Server shut down normally\n");
        } else {
            _outputArea.append("*** Server died with exit code " + exitCode + "\n");
        }
        updateServerState();
        _startStopButton.setEnabled(true);
    }
}
