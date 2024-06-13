import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Document {
    private String title;
    private String content;

    public Document(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}

class SearchEngine {
    private List<Document> documents;

    public SearchEngine() {
        documents = new ArrayList<>();
    }

    public void addDocument(Document document) {
        documents.add(document);
    }

    public List<Document> search(String keyword) {
        List<Document> results = new ArrayList<>();
        for (Document document : documents) {
            if (document.getContent().toLowerCase().contains(keyword.toLowerCase()) ||
                    document.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(document);
            }
        }
        return results;
    }
}

class SearchEngineGUI extends JFrame {
    private JTextField searchField;
    private JTextArea resultArea;
    private SearchEngine searchEngine;
    private Document currentDocument;

    public SearchEngineGUI() {
        setTitle("Search Engine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        searchEngine = new SearchEngine();
        currentDocument = null;
        addComponents();
    }

    private void addComponents() {
        // Top panel with file, edit, and view buttons
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE);

        JButton fileButton = new JButton("File");
        fileButton.setFont(new Font("Arial", Font.BOLD, 12));
        topPanel.add(fileButton);

        JButton editButton = new JButton("Edit");
        editButton.setFont(new Font("Arial", Font.BOLD, 12));
        topPanel.add(editButton);

        JButton viewButton = new JButton("View");
        viewButton.setFont(new Font("Arial", Font.BOLD, 12));
        topPanel.add(viewButton);

        add(topPanel, BorderLayout.NORTH);

        // Center panel for search components
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setBackground(Color.WHITE);

        JLabel searchLabel = new JLabel("Enter a keyword:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 14));
        searchPanel.add(searchLabel);

        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 12));
        searchPanel.add(searchField);

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 12));
        searchButton.setPreferredSize(new Dimension(90, 30));
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
        searchPanel.add(searchButton);

        centerPanel.add(searchPanel, BorderLayout.NORTH);

        add(centerPanel, BorderLayout.CENTER);

        // Result area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 12));

        add(new JScrollPane(resultArea), BorderLayout.SOUTH);

        // File button actions
        fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int choice = fileChooser.showSaveDialog(SearchEngineGUI.this);

                if (choice == JFileChooser.APPROVE_OPTION) {
                    saveDocument(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });
    }

    private void performSearch() {
        String keyword = searchField.getText();
        List<Document> results = searchEngine.search(keyword);
        displayResults(results);
    }

    private void displayResults(List<Document> results) {
        resultArea.setText("");
        if (results.isEmpty()) {
            resultArea.append("No documents found.");
        } else {
            for (Document document : results) {
                resultArea.append("Title: " + document.getTitle() + "\n");
                resultArea.append("Content: " + document.getContent() + "\n\n");
            }
        }
    }

    private void saveDocument(String filePath) {
        if (currentDocument != null) {
            JOptionPane.showMessageDialog(this, "Document saved: " + filePath);
        }
    }

    public void addDocument(Document document) {
        searchEngine.addDocument(document);
        currentDocument = document;
    }
}

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SearchEngineGUI searchEngineGUI = new SearchEngineGUI();
                searchEngineGUI.setVisible(true);

                searchEngineGUI.addDocument(new Document("Document 1", "This is the content of document 1."));
                searchEngineGUI.addDocument(new Document("Document 2", "This is the content of document 2."));
                searchEngineGUI.addDocument(new Document("Document 3", "This is the content of document 3."));
            }
        });
    }
}
