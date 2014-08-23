package gui;

import domain.Category;
import domain.Client;
import domain.Question;
import game.logic.Controller;
import game.logic.GameController;
import game.logic.Validate;
import gui.utils.CategoryTableModel;
import gui.utils.ClientTableModel;
import gui.utils.QuestionTableModel;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Lazar Ristic <lazar.ristic91@gmail.com>
 * @author Jelena Tabas
 * @version 0.92
 */
public class MainInputForm extends javax.swing.JFrame {

    /**
     * Creates new form MainInputForma
     */
    public MainInputForm() {
        initComponents();
        fixForm();
    }

    public void refreshStatistics() {
        if (Controller.getInstance().getHighScores().getYearlyHighScore() != null) {
            jTFYearlyHighScore.setText(Controller.getInstance().getHighScores().getYearlyHighScore().toString());
        } else {
            jTFYearlyHighScore.setText(0 + "");
        }
        if (Controller.getInstance().getHighScores().getMonthlyHighScore() != null) {
            jTFMonthlyHighScore.setText(Controller.getInstance().getHighScores().getMonthlyHighScore().toString());
        } else {
            jTFMonthlyHighScore.setText(0 + "");
        }
        if (Controller.getInstance().getHighScores().getTodayHighScore() != null) {
            jTFtodayHighScore.setText(Controller.getInstance().getHighScores().getTodayHighScore().toString());
        } else {
            jTFtodayHighScore.setText(0 + "");
        }
    }

    /**
     * Creates JTree in JTreeAddQuestion with event that adds new question with
     * InpurDialog for name of question, true answer and 3 false answers
     */
    public void AddQuestion() {
        final JTree JTreeAddQuestion;

        //create the rootOfQuestion node
        DefaultMutableTreeNode rootOfQuestion = new DefaultMutableTreeNode(Controller.getInstance().getCategoryTree().getName());

        //create the clild nodes and add them to the rootOfQuestion node
        recursionAddCategory(Controller.getInstance().getCategoryTree().getSubCategories(), rootOfQuestion);

        //create the JTreeAddCategory by passing in the rootOfQuestion node
        JTreeAddQuestion = new JTree(rootOfQuestion);
        add(JTreeAddQuestion);

        //some options
        JTreeAddQuestion.setShowsRootHandles(true);
        JTreeAddQuestion.setRootVisible(false);
        jTPAdd.addTab("Dodaj Pitanje", JTreeAddQuestion);

        JTreeAddQuestion.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) JTreeAddQuestion.getLastSelectedPathComponent();
                //GameController.getInstance().loadNewTest(selectedNode.getUserObject().toString());

                if (!selectedNode.isLeaf()) {
                    return;
                }
                String s = (String) JOptionPane.showInputDialog(jTPAdd, "Unesi pitanje", ""
                        + "Unesite tekst pitanja", JOptionPane.PLAIN_MESSAGE, null, null, "");

                //If a string was returned, say so.
                if ((s != null) && (s.length() > 0)) {
                    //Controller.getInstance().addCategory(s, selectedNode.getUserObject().toString());
                    Question q = new Question();
                    q.setText(s);
                    q.setCategory(Controller.getInstance().getCategory(selectedNode.getUserObject().toString()));
                    Controller.getInstance().addQuestion(q);
                    JOptionPane.showMessageDialog(rootPane, "Uneto pitanje je zapamceno");
                    return;
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Sistem ne moze da zapamti novo pitanje");
                }

                //If you're here, the return value was null/empty.
                setLabel("pitanje");

            }

            private void setLabel(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

    }

    public void AddCategory() {
        final JTree JTreeAddCategory;

        //create the rootOfQuestion node
        DefaultMutableTreeNode rootOfCategory = new DefaultMutableTreeNode(Controller.getInstance().getCategoryTree().getName());
        //create the clild nodes and add them to the rootOfQuestion node

        recursionAddCategory(Controller.getInstance().getCategoryTree().getSubCategories(), rootOfCategory);

        //create the JTreeAddCategory by passing in the rootOfQuestion node
        JTreeAddCategory = new JTree(rootOfCategory);
        add(JTreeAddCategory);

        JTreeAddCategory.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) JTreeAddCategory.getLastSelectedPathComponent();
                //GameController.getInstance().loadNewTest(selectedNode.getUserObject().toString());

                String s = (String) JOptionPane.showInputDialog(jTPAdd, "Nazovi novu kategoriju",
                        "Nova kategorija", JOptionPane.PLAIN_MESSAGE, null, null, "");

                //If a string was returned, say so.
                if ((s != null) && (s.length() > 0)) {
                    Controller.getInstance().addCategory(s, selectedNode.getUserObject().toString());

                    return;
                }

                //If you're here, the return value was null/empty.
                setLabel("Come on, finish the sentence!");

            }

            private void setLabel(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

        //some options
        JTreeAddCategory.setShowsRootHandles(true);
        JTreeAddCategory.setRootVisible(false);
        jTPAdd.addTab("Dodaj Kategoriju", JTreeAddCategory);
    }

    public void PlaySelectCategory() {
        final JTree JTreePlaySelectCategory;

        //create the rootOfQuestion node
        DefaultMutableTreeNode rootOfCategory = new DefaultMutableTreeNode(Controller.getInstance().getCategoryTree().getName());
        //create the clild nodes and add them to the rootOfQuestion node

        recursionAddCategory(Controller.getInstance().getCategoryTree().getSubCategories(), rootOfCategory);

        //create the JTreeAddCategory by passing in the rootOfQuestion node
        JTreePlaySelectCategory = new JTree(rootOfCategory);
        add(JTreePlaySelectCategory);

        JTreePlaySelectCategory.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                try {
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) JTreePlaySelectCategory.getLastSelectedPathComponent();
                    if (!selectedNode.isLeaf()) {
                        return;
                    }
                    GameController.getInstance().loadNewTest(selectedNode.getUserObject().toString());
                    PlayFrame playFrame = new PlayFrame();
                    playFrame.setVisible(true);
                } catch (NullPointerException ne) {
                    JOptionPane.showMessageDialog(rootPane, "Sistem ne moze dа pokrene zadatu igru", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //some options
        JTreePlaySelectCategory.setShowsRootHandles(true);
        JTreePlaySelectCategory.setRootVisible(false);
        jTPPlaySetCategory.addTab("Izaberite Kategoriju", JTreePlaySelectCategory);
    }

    public void DeleteCategory() {
        final JTree JTreeDeleteCategory;

        //create the rootOfQuestion node
        DefaultMutableTreeNode rootOfCategory = new DefaultMutableTreeNode(Controller.getInstance().getCategoryTree().getName());
        //create the clild nodes and add them to the rootOfQuestion node

        recursionAddCategory(Controller.getInstance().getCategoryTree().getSubCategories(), rootOfCategory);

        //create the JTreeAddCategory by passing in the rootOfQuestion node
        JTreeDeleteCategory = new JTree(rootOfCategory);
        add(JTreeDeleteCategory);

        //some options
        JTreeDeleteCategory.setShowsRootHandles(true);
        JTreeDeleteCategory.setRootVisible(false);
        jTPAdd.addTab("Izbrisi Kategoriju", JTreeDeleteCategory);

        //actionEvent
        JTreeDeleteCategory.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) JTreeDeleteCategory.getLastSelectedPathComponent();
                if (!selectedNode.isLeaf()) {
                    return;
                }
                Controller.getInstance().deleteCategory(selectedNode.getUserObject().toString());
                //custom title, no icon
                JOptionPane.showMessageDialog(jTPAdd,
                        "Eggs are not supposed to be green.",
                        "Uspesno!",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });
    }

    /**
     * Creates JTree in JTreeDeleteQuestion with event that changes question
     * with InputDialog for new name of question.
     */
    public void DeleteQuestion() {
        final JTree JTreeDeleteQuestion;

        //create the rootOfQuestion node
        DefaultMutableTreeNode rootOfCategory = new DefaultMutableTreeNode(Controller.getInstance().getCategoryTree().getName());
        //create the clild nodes and add them to the rootOfQuestion node

        recursionAddCategory(Controller.getInstance().getCategoryTree().getSubCategories(), rootOfCategory);

        //create the JTreeAddCategory by passing in the rootOfQuestion node
        JTreeDeleteQuestion = new JTree(rootOfCategory);
        add(JTreeDeleteQuestion);

        //some options
        JTreeDeleteQuestion.setShowsRootHandles(true);
        JTreeDeleteQuestion.setRootVisible(false);
        jTPAdd.addTab("Izbrisi Pitanje", JTreeDeleteQuestion);

        //actionEvent
        JTreeDeleteQuestion.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) JTreeDeleteQuestion.getLastSelectedPathComponent();
                //Controller.getInstance().deleteCategory(selectedNode.getUserObject().toString());
                //custom title, no icon
            /*    JOptionPane.showMessageDialog(jTPAdd,
                 "Pitanje je izbrisano",
                 "Uspesno!",
                 JOptionPane.PLAIN_MESSAGE);
                 */
                Category category = Controller.getInstance().getCategory(selectedNode.getUserObject().toString());
                Controller.getInstance().loadApprovedQuestions(category);
                ChangeQuestion changeQuestion = new ChangeQuestion(false);
                changeQuestion.setVisible(true);
                JOptionPane.showMessageDialog(rootPane, "Sistem je nasao pitanja po zadatoj vrednosti");

            }
        });
    }

    public void changeCategory() {
        final JTree jTreeChangeCategory;

        //create the rootOfQuestion node
        DefaultMutableTreeNode rootOfCategory = new DefaultMutableTreeNode(Controller.getInstance().getCategoryTree().getName());
        //create the clild nodes and add them to the rootOfQuestion node

        recursionAddCategory(Controller.getInstance().getCategoryTree().getSubCategories(), rootOfCategory);

        //create the JTreeAddCategory by passing in the rootOfQuestion node
        jTreeChangeCategory = new JTree(rootOfCategory);
        add(jTreeChangeCategory);

        //some options
        jTreeChangeCategory.setShowsRootHandles(true);
        jTreeChangeCategory.setRootVisible(false);
        jTPAdd.addTab("Promeni Kategoriju", jTreeChangeCategory);

        //actionEvent
        jTreeChangeCategory.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTreeChangeCategory.getLastSelectedPathComponent();
                //GameController.getInstance().loadNewTest(selectedNode.getUserObject().toString());

                String s = (String) JOptionPane.showInputDialog(jTPAdd, "Promeni naziv kategorije",
                        "Customized Dialog", JOptionPane.PLAIN_MESSAGE, null, null, "");

                //If a string was returned, say so.
                if ((s != null) && (s.length() > 0)) {
                    Controller.getInstance().changeCategory(selectedNode.getUserObject().toString(), s);
                    return;
                }

                //If you're here, the return value was null/empty.
                setLabel("Come on, finish the sentence!");

            }

            private void setLabel(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    public void changeQuestion() {
        final JTree jTreeChangeQuestion;

        //create the rootOfQuestion node
        DefaultMutableTreeNode rootOfCategory = new DefaultMutableTreeNode(Controller.getInstance().getCategoryTree().getName());
        //create the clild nodes and add them to the rootOfQuestion node

        recursionAddCategory(Controller.getInstance().getCategoryTree().getSubCategories(), rootOfCategory);

        //create the JTreeAddCategory by passing in the rootOfQuestion node
        jTreeChangeQuestion = new JTree(rootOfCategory);
        add(jTreeChangeQuestion);

        //some options
        jTreeChangeQuestion.setShowsRootHandles(true);
        jTreeChangeQuestion.setRootVisible(false);
        jTPAdd.addTab("Promeni Pitanje", jTreeChangeQuestion);

        //actionEvent
        jTreeChangeQuestion.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTreeChangeQuestion.getLastSelectedPathComponent();
                //GameController.getInstance().loadNewTest(selectedNode.getUserObject().toString());
                Category category = Controller.getInstance().getCategory(selectedNode.getUserObject().toString());
                Controller.getInstance().loadApprovedQuestions(category);
                ChangeQuestion changeQuestion = new ChangeQuestion(true);
                changeQuestion.setVisible(true);
                JOptionPane.showMessageDialog(rootPane, "Sistem je nasao pitanja po zadatoj vrednosti");

            }

        });

    }

    public void recursionAddCategory(List<Category> list, DefaultMutableTreeNode defaultMutableTreeNode) {

        if (list == null) {
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).isApproved()) {
                continue;
            }
            DefaultMutableTreeNode mutableTreeNode = new DefaultMutableTreeNode(list.get(i).getName());
            defaultMutableTreeNode.add(mutableTreeNode);
            List<Category> categorys = list.get(i).getSubCategories();
            recursionAddCategory(categorys, mutableTreeNode);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelDesktop = new javax.swing.JPanel();
        jLUserNameDesktop = new javax.swing.JLabel();
        jBAdmin = new javax.swing.JButton();
        jBAccount = new javax.swing.JButton();
        jBHelp = new javax.swing.JButton();
        jBPlay = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jTPAdmin = new javax.swing.JTabbedPane();
        jPApproveCategory = new javax.swing.JPanel();
        jButtonApproveCategory = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jbApproveCategories = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        autocompleteJCBCategory1 = new gui.utils.AutocompleteJCBCategory();
        jPApproveQuestion = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        autocompleteJCBQuestion1 = new gui.utils.AutocompleteJCBQuestion();
        jPDeleteUser = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        autocompleteJCBClient1 = new gui.utils.AutocompleteJCBClient();
        jTPAccount = new javax.swing.JTabbedPane();
        jPEditUserInformation = new javax.swing.JPanel();
        jLFirstNameEdit = new javax.swing.JLabel();
        jLLastNameEdit = new javax.swing.JLabel();
        jLEmailEdit = new javax.swing.JLabel();
        jTFFirstName = new javax.swing.JTextField();
        jTFLastName = new javax.swing.JTextField();
        jTFEmail = new javax.swing.JTextField();
        jBChangeUserInfo = new javax.swing.JButton();
        jPChangePassword = new javax.swing.JPanel();
        jLOldPassword = new javax.swing.JLabel();
        jLNewPassword = new javax.swing.JLabel();
        jLRepeatNewPassword = new javax.swing.JLabel();
        jBChangePassword = new javax.swing.JButton();
        jOldPasswordField = new javax.swing.JPasswordField();
        jNewPasswordField = new javax.swing.JPasswordField();
        jNewRepeatPasswordField = new javax.swing.JPasswordField();
        jPHighScore = new javax.swing.JPanel();
        jLYearlyHighScore = new javax.swing.JLabel();
        jLMonthlyHighScore = new javax.swing.JLabel();
        jLTodayHighScore = new javax.swing.JLabel();
        jTFYearlyHighScore = new javax.swing.JTextField();
        jTFMonthlyHighScore = new javax.swing.JTextField();
        jTFtodayHighScore = new javax.swing.JTextField();
        jPDeleteClient = new javax.swing.JPanel();
        jbDeleteAccount = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTPPlaySetCategory = new javax.swing.JTabbedPane();
        jTPAdd = new javax.swing.JTabbedPane();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(720, 480));
        setPreferredSize(new java.awt.Dimension(720, 480));

        jPanelDesktop.setFocusCycleRoot(true);
        jPanelDesktop.setPreferredSize(new java.awt.Dimension(720, 480));

        jLUserNameDesktop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLUserNameDesktop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLUserNameDesktopMouseClicked(evt);
            }
        });

        jBAdmin.setText("Admin");
        jBAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAdminActionPerformed(evt);
            }
        });

        jBAccount.setText("Nalog");
        jBAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAccountActionPerformed(evt);
            }
        });

        jBHelp.setText("Pomoć");
        jBHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBHelpActionPerformed(evt);
            }
        });

        jBPlay.setText("<html>Okreni na<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Igru</html>");
        jBPlay.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBPlayActionPerformed(evt);
            }
        });

        jLayeredPane1.setPreferredSize(new java.awt.Dimension(720, 480));

        jButtonApproveCategory.setText("Dodaj");
        jButtonApproveCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonApproveCategoryActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jbApproveCategories.setText("Sacuvaj");
        jbApproveCategories.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbApproveCategoriesActionPerformed(evt);
            }
        });

        jButton3.setText("Resetuj");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPApproveCategoryLayout = new javax.swing.GroupLayout(jPApproveCategory);
        jPApproveCategory.setLayout(jPApproveCategoryLayout);
        jPApproveCategoryLayout.setHorizontalGroup(
            jPApproveCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPApproveCategoryLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(autocompleteJCBCategory1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonApproveCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPApproveCategoryLayout.createSequentialGroup()
                .addGap(0, 408, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jbApproveCategories, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66))
            .addGroup(jPApproveCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPApproveCategoryLayout.createSequentialGroup()
                    .addContainerGap(42, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(64, Short.MAX_VALUE)))
        );
        jPApproveCategoryLayout.setVerticalGroup(
            jPApproveCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPApproveCategoryLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPApproveCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonApproveCategory)
                    .addComponent(autocompleteJCBCategory1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 215, Short.MAX_VALUE)
                .addGroup(jPApproveCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jbApproveCategories))
                .addGap(123, 123, 123))
            .addGroup(jPApproveCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPApproveCategoryLayout.createSequentialGroup()
                    .addContainerGap(103, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(179, Short.MAX_VALUE)))
        );

        jTPAdmin.addTab("Odobri Kategorije", jPApproveCategory);

        jButton4.setText("Dodaj");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jButton5.setText("Sacuvaj");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Resetuj");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPApproveQuestionLayout = new javax.swing.GroupLayout(jPApproveQuestion);
        jPApproveQuestion.setLayout(jPApproveQuestionLayout);
        jPApproveQuestionLayout.setHorizontalGroup(
            jPApproveQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPApproveQuestionLayout.createSequentialGroup()
                .addGroup(jPApproveQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPApproveQuestionLayout.createSequentialGroup()
                        .addGap(419, 419, 419)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPApproveQuestionLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(autocompleteJCBQuestion1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36)
                .addGroup(jPApproveQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
                .addContainerGap(61, Short.MAX_VALUE))
            .addGroup(jPApproveQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPApproveQuestionLayout.createSequentialGroup()
                    .addContainerGap(55, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(61, Short.MAX_VALUE)))
        );
        jPApproveQuestionLayout.setVerticalGroup(
            jPApproveQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPApproveQuestionLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPApproveQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(autocompleteJCBQuestion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 208, Short.MAX_VALUE)
                .addGroup(jPApproveQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton5))
                .addGap(116, 116, 116))
            .addGroup(jPApproveQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPApproveQuestionLayout.createSequentialGroup()
                    .addContainerGap(119, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(177, Short.MAX_VALUE)))
        );

        jTPAdmin.addTab("Odobri Pitanja", jPApproveQuestion);

        jButton7.setText("Dodaj");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        jButton8.setText("Sacuvaj");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("Resetuj");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPDeleteUserLayout = new javax.swing.GroupLayout(jPDeleteUser);
        jPDeleteUser.setLayout(jPDeleteUserLayout);
        jPDeleteUserLayout.setHorizontalGroup(
            jPDeleteUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPDeleteUserLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(autocompleteJCBClient1, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPDeleteUserLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
            .addGroup(jPDeleteUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPDeleteUserLayout.createSequentialGroup()
                    .addContainerGap(61, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(45, Short.MAX_VALUE)))
        );
        jPDeleteUserLayout.setVerticalGroup(
            jPDeleteUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPDeleteUserLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPDeleteUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(autocompleteJCBClient1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 219, Short.MAX_VALUE)
                .addGroup(jPDeleteUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton9)
                    .addComponent(jButton8))
                .addGap(100, 100, 100))
            .addGroup(jPDeleteUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPDeleteUserLayout.createSequentialGroup()
                    .addContainerGap(119, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(157, Short.MAX_VALUE)))
        );

        jTPAdmin.addTab("Izbrisi Korisnike", jPDeleteUser);

        jTPAccount.setPreferredSize(new java.awt.Dimension(720, 480));

        jLFirstNameEdit.setText("Ime:");

        jLLastNameEdit.setText("Prezime:");

        jLEmailEdit.setText("Email:");

        jTFFirstName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFFirstNameActionPerformed(evt);
            }
        });

        jTFEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFEmailActionPerformed(evt);
            }
        });

        jBChangeUserInfo.setText("Promeni");
        jBChangeUserInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBChangeUserInfoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPEditUserInformationLayout = new javax.swing.GroupLayout(jPEditUserInformation);
        jPEditUserInformation.setLayout(jPEditUserInformationLayout);
        jPEditUserInformationLayout.setHorizontalGroup(
            jPEditUserInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPEditUserInformationLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPEditUserInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBChangeUserInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPEditUserInformationLayout.createSequentialGroup()
                        .addGroup(jPEditUserInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLLastNameEdit, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                            .addComponent(jLFirstNameEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLEmailEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(67, 67, 67)
                        .addGroup(jPEditUserInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTFFirstName, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jTFLastName)
                            .addComponent(jTFEmail))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPEditUserInformationLayout.setVerticalGroup(
            jPEditUserInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPEditUserInformationLayout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(jPEditUserInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLFirstNameEdit)
                    .addComponent(jTFFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPEditUserInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLLastNameEdit)
                    .addComponent(jTFLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPEditUserInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLEmailEdit)
                    .addComponent(jTFEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBChangeUserInfo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTPAccount.addTab("Promeni informacije", jPEditUserInformation);

        jLOldPassword.setText("Unesite staru šifru:");

        jLNewPassword.setText("Unesite novu šifru:");

        jLRepeatNewPassword.setText("Ponovite novu šifru:");

        jBChangePassword.setText("Promeni");
        jBChangePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBChangePasswordActionPerformed(evt);
            }
        });

        jNewPasswordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNewPasswordFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPChangePasswordLayout = new javax.swing.GroupLayout(jPChangePassword);
        jPChangePassword.setLayout(jPChangePasswordLayout);
        jPChangePasswordLayout.setHorizontalGroup(
            jPChangePasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPChangePasswordLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPChangePasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBChangePassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPChangePasswordLayout.createSequentialGroup()
                        .addGroup(jPChangePasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLRepeatNewPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLNewPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLOldPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(80, 80, 80)
                        .addGroup(jPChangePasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jOldPasswordField)
                            .addComponent(jNewPasswordField)
                            .addComponent(jNewRepeatPasswordField, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPChangePasswordLayout.setVerticalGroup(
            jPChangePasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPChangePasswordLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(jPChangePasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLOldPassword)
                    .addComponent(jOldPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPChangePasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLNewPassword)
                    .addComponent(jNewPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPChangePasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLRepeatNewPassword)
                    .addComponent(jNewRepeatPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBChangePassword)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTPAccount.addTab("Promeni Sifru", jPChangePassword);

        jLYearlyHighScore.setText("Godisnjak:");

        jLMonthlyHighScore.setText(" Mesecnik:");

        jLTodayHighScore.setText("Igrac Dana:");

        jTFYearlyHighScore.setEditable(false);
        jTFYearlyHighScore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFYearlyHighScoreActionPerformed(evt);
            }
        });

        jTFMonthlyHighScore.setEditable(false);
        jTFMonthlyHighScore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFMonthlyHighScoreActionPerformed(evt);
            }
        });

        jTFtodayHighScore.setEditable(false);
        jTFtodayHighScore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFtodayHighScoreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPHighScoreLayout = new javax.swing.GroupLayout(jPHighScore);
        jPHighScore.setLayout(jPHighScoreLayout);
        jPHighScoreLayout.setHorizontalGroup(
            jPHighScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPHighScoreLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPHighScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLMonthlyHighScore)
                    .addComponent(jLTodayHighScore)
                    .addComponent(jLYearlyHighScore))
                .addGap(68, 68, 68)
                .addGroup(jPHighScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTFYearlyHighScore, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                    .addComponent(jTFMonthlyHighScore, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                    .addComponent(jTFtodayHighScore, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
                .addContainerGap(436, Short.MAX_VALUE))
        );
        jPHighScoreLayout.setVerticalGroup(
            jPHighScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPHighScoreLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPHighScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLYearlyHighScore)
                    .addComponent(jTFYearlyHighScore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(jPHighScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFMonthlyHighScore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLMonthlyHighScore))
                .addGap(40, 40, 40)
                .addGroup(jPHighScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLTodayHighScore)
                    .addComponent(jTFtodayHighScore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(238, Short.MAX_VALUE))
        );

        jTPAccount.addTab("Statistika", jPHighScore);

        jbDeleteAccount.setText("Izbrisi nalog");
        jbDeleteAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDeleteAccountActionPerformed(evt);
            }
        });

        jLabel1.setText("Jednom obrisani nalog se ne može vratiti.");

        javax.swing.GroupLayout jPDeleteClientLayout = new javax.swing.GroupLayout(jPDeleteClient);
        jPDeleteClient.setLayout(jPDeleteClientLayout);
        jPDeleteClientLayout.setHorizontalGroup(
            jPDeleteClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPDeleteClientLayout.createSequentialGroup()
                .addGap(290, 290, 290)
                .addComponent(jbDeleteAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPDeleteClientLayout.createSequentialGroup()
                .addContainerGap(222, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(207, 207, 207))
        );
        jPDeleteClientLayout.setVerticalGroup(
            jPDeleteClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPDeleteClientLayout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(jLabel1)
                .addGap(36, 36, 36)
                .addComponent(jbDeleteAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(175, Short.MAX_VALUE))
        );

        jTPAccount.addTab("Izbrisi nalog", jPDeleteClient);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jTPAccount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jTPAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTPPlaySetCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jTPAdmin)
                    .addGap(20, 20, 20)))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jTPAccount, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTPAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTPPlaySetCategory, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jTPAdmin)))
        );
        jLayeredPane1.setLayer(jTPAdmin, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jTPAccount, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jTPPlaySetCategory, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jTPAdd, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jButton1.setText("Promeni");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelDesktopLayout = new javax.swing.GroupLayout(jPanelDesktop);
        jPanelDesktop.setLayout(jPanelDesktopLayout);
        jPanelDesktopLayout.setHorizontalGroup(
            jPanelDesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDesktopLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jBPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelDesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addGroup(jPanelDesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBHelp, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                    .addComponent(jBAccount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addComponent(jLUserNameDesktop, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
            .addGroup(jPanelDesktopLayout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanelDesktopLayout.setVerticalGroup(
            jPanelDesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDesktopLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanelDesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBPlay, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDesktopLayout.createSequentialGroup()
                        .addGroup(jPanelDesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBAccount)
                            .addComponent(jBAdmin))
                        .addGap(5, 5, 5)
                        .addGroup(jPanelDesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBHelp)
                            .addComponent(jButton1)))
                    .addComponent(jLUserNameDesktop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelDesktop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelDesktop, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAccountActionPerformed
        // TODO add your handling code here:

        jTPAdmin.setVisible(false);
        jTPAccount.setVisible(true);
        jTPAdd.setVisible(false);
        jTPPlaySetCategory.setVisible(false);
        jTFFirstName.setText(Controller.getInstance().getActiveClient().getFirstName());
        jTFLastName.setText(Controller.getInstance().getActiveClient().getLastName());
        jTFEmail.setText(Controller.getInstance().getActiveClient().getEmail());
    }//GEN-LAST:event_jBAccountActionPerformed

    private void jBHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBHelpActionPerformed
        // TODO add your handling code here:
        jTPAccount.setVisible(false);
        jTPAdmin.setVisible(false);
        jTPAdd.setVisible(false);
        jTPPlaySetCategory.setVisible(false);
    }//GEN-LAST:event_jBHelpActionPerformed

    private void jTFFirstNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFFirstNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFFirstNameActionPerformed

    private void jTFEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFEmailActionPerformed

    private void jBChangeUserInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBChangeUserInfoActionPerformed
        // TODO add your handling code here:

        String firstName = jTFFirstName.getText();
        String lastName = jTFLastName.getText();
        String email = jTFEmail.getText();

        Client client = Controller.getInstance().getActiveClient();
        client.setFirstName(firstName);
        client.setEmail(email);
        client.setLastName(lastName);
        try {
            Controller.getInstance().changeClientInformation();
            JOptionPane.showMessageDialog(rootPane, "Uspesno sacuvan profil");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, "Sistem ne moze da zapamti novi profil", "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jBChangeUserInfoActionPerformed

    private void jBChangePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBChangePasswordActionPerformed
        // TODO add your handling code here:

        String oldPaswword = new String(jOldPasswordField.getPassword());
        String newPassword = new String(jNewPasswordField.getPassword());
        String repeatNewPassword = new String(jNewRepeatPasswordField.getPassword());

        Validate validate = new Validate();
        try {
            //validate.changePassword(oldPaswword, newPassword, repeatNewPassword);
            if (validate.changePassword(oldPaswword, newPassword, repeatNewPassword)) {
                Controller.getInstance().getActiveClient().setPassword(newPassword);
                Controller.getInstance().changeClientInformation();
                JOptionPane.showMessageDialog(this,
                        "Uspesno ste promenili lozinku!",
                        "Uspesno",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Nova sifra ima manje od 8 karaktera",
                        "Greska!",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Greska!",
                    JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jBChangePasswordActionPerformed

    private void jBAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAdminActionPerformed
        // TODO add your handling code here:

        jTPAdmin.setVisible(true);
        jTPAccount.setVisible(false);
        jTPAdd.setVisible(false);
        jTPPlaySetCategory.setVisible(false);
    }//GEN-LAST:event_jBAdminActionPerformed

    private void jNewPasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNewPasswordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jNewPasswordFieldActionPerformed

    private void jLUserNameDesktopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLUserNameDesktopMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLUserNameDesktopMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        jTPAdmin.setVisible(false);
        jTPAccount.setVisible(false);
        jTPAdd.setVisible(true);
        jTPPlaySetCategory.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jBPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBPlayActionPerformed
        jTPPlaySetCategory.setVisible(true);
        jTPAdmin.setVisible(false);
        jTPAccount.setVisible(false);
        jTPAdd.setVisible(false);
    }//GEN-LAST:event_jBPlayActionPerformed

    private void jTFtodayHighScoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFtodayHighScoreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFtodayHighScoreActionPerformed

    private void jTFMonthlyHighScoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFMonthlyHighScoreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFMonthlyHighScoreActionPerformed

    private void jTFYearlyHighScoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFYearlyHighScoreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFYearlyHighScoreActionPerformed

    private void jButtonApproveCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonApproveCategoryActionPerformed
        if (((Category) autocompleteJCBCategory1.getSelectedItem()).getId() == 0) {
            return;
        }
        if (Controller.getInstance().getApproveCategories().contains(((Category) autocompleteJCBCategory1.getSelectedItem()))) {
            return;
        }
        Controller.getInstance().addApproveCategory((Category) autocompleteJCBCategory1.getSelectedItem());
        ((CategoryTableModel) jTable1.getModel()).fireTableDataChanged();
    }//GEN-LAST:event_jButtonApproveCategoryActionPerformed

    private void jbApproveCategoriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbApproveCategoriesActionPerformed
        Controller.getInstance().executeAdmin(new Category());
        ((CategoryTableModel) jTable1.getModel()).fireTableDataChanged();
    }//GEN-LAST:event_jbApproveCategoriesActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Controller.getInstance().getApproveCategories().clear();
        ((CategoryTableModel) jTable1.getModel()).fireTableDataChanged();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (((Question) autocompleteJCBQuestion1.getSelectedItem()).getId() == 0) {
            return;
        }
        if (Controller.getInstance().getApproveQuestions().contains(((Question) autocompleteJCBQuestion1.getSelectedItem()))) {
            return;
        }
        Controller.getInstance().addApproveQuestion((Question) autocompleteJCBQuestion1.getSelectedItem());
        ((QuestionTableModel) jTable2.getModel()).fireTableDataChanged();
        JOptionPane.showMessageDialog(this, "Sistem je nasao pitanja po zadatoj vrednosti");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        Controller.getInstance().executeAdmin(new Question());
        ((QuestionTableModel) jTable2.getModel()).fireTableDataChanged();
        JOptionPane.showMessageDialog(this, "Sistem je odobrio izabrana nova pitanja");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        Controller.getInstance().getApproveQuestions().clear();
        ((QuestionTableModel) jTable2.getModel()).fireTableDataChanged();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if (((Client) autocompleteJCBClient1.getSelectedItem()).getId() == 0) {
            return;
        }
        if (Controller.getInstance().getDeleteClients().contains(((Client) autocompleteJCBClient1.getSelectedItem()))) {
            return;
        }
        Controller.getInstance().addDeletedClient((Client) autocompleteJCBClient1.getSelectedItem());

        ((ClientTableModel) jTable3.getModel()).fireTableDataChanged();
        //JOptionPane.showMessageDialog(this, "Sistem je našao korisnike po zadatoj vrednosti");
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        try {
            Controller.getInstance().deleteClients(Controller.getInstance().getDeleteClients());
            ((ClientTableModel) jTable3.getModel()).fireTableDataChanged();
            JOptionPane.showMessageDialog(this, "Izabrani korisnici su izbrisani");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, "Sistem nije uspesno obrisao korisnike", "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        Controller.getInstance().getDeleteClients().clear();
        ((ClientTableModel) jTable3.getModel()).fireTableDataChanged();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jbDeleteAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDeleteAccountActionPerformed
        try {
            Controller.getInstance().deleteClient(Controller.getInstance().getActiveClient());
            LogInForm form = new LogInForm();
            form.setVisible(true);
            this.dispose();
            JOptionPane.showMessageDialog(rootPane, "Sistem je obrisao vas profil");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, "Sistem ne moze dа izbrise profil", "Greska", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jbDeleteAccountActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private gui.utils.AutocompleteJCBCategory autocompleteJCBCategory1;
    private gui.utils.AutocompleteJCBClient autocompleteJCBClient1;
    private gui.utils.AutocompleteJCBQuestion autocompleteJCBQuestion1;
    private javax.swing.JButton jBAccount;
    private javax.swing.JButton jBAdmin;
    private javax.swing.JButton jBChangePassword;
    private javax.swing.JButton jBChangeUserInfo;
    private javax.swing.JButton jBHelp;
    private javax.swing.JButton jBPlay;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButtonApproveCategory;
    private javax.swing.JLabel jLEmailEdit;
    private javax.swing.JLabel jLFirstNameEdit;
    private javax.swing.JLabel jLLastNameEdit;
    private javax.swing.JLabel jLMonthlyHighScore;
    private javax.swing.JLabel jLNewPassword;
    private javax.swing.JLabel jLOldPassword;
    private javax.swing.JLabel jLRepeatNewPassword;
    private javax.swing.JLabel jLTodayHighScore;
    private javax.swing.JLabel jLUserNameDesktop;
    private javax.swing.JLabel jLYearlyHighScore;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPasswordField jNewPasswordField;
    private javax.swing.JPasswordField jNewRepeatPasswordField;
    private javax.swing.JPasswordField jOldPasswordField;
    private javax.swing.JPanel jPApproveCategory;
    private javax.swing.JPanel jPApproveQuestion;
    private javax.swing.JPanel jPChangePassword;
    private javax.swing.JPanel jPDeleteClient;
    private javax.swing.JPanel jPDeleteUser;
    private javax.swing.JPanel jPEditUserInformation;
    private javax.swing.JPanel jPHighScore;
    private javax.swing.JPanel jPanelDesktop;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTFEmail;
    private javax.swing.JTextField jTFFirstName;
    private javax.swing.JTextField jTFLastName;
    private javax.swing.JTextField jTFMonthlyHighScore;
    private javax.swing.JTextField jTFYearlyHighScore;
    private javax.swing.JTextField jTFtodayHighScore;
    private javax.swing.JTabbedPane jTPAccount;
    private javax.swing.JTabbedPane jTPAdd;
    private javax.swing.JTabbedPane jTPAdmin;
    private javax.swing.JTabbedPane jTPPlaySetCategory;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JButton jbApproveCategories;
    private javax.swing.JButton jbDeleteAccount;
    // End of variables declaration//GEN-END:variables

    private void fixForm() {
        jTPAdmin.setVisible(false);
        jTPAccount.setVisible(false);
        jTPAdd.setVisible(false);
        if (!Controller.getInstance().getActiveClient().isAdministrator()) {
            jBAdmin.setVisible(false);
        }
        jTPPlaySetCategory.setVisible(false);
        jLUserNameDesktop.setText(Controller.getInstance().getActiveClient().getUsername());
        AddCategory();
        AddQuestion();
        PlaySelectCategory();
        DeleteCategory();
        DeleteQuestion();
        changeCategory();
        changeQuestion();
        Controller.getInstance().loadHighScores();
        refreshStatistics();
        jTable1.setModel(new CategoryTableModel());
        jTable2.setModel(new QuestionTableModel());
        jTable3.setModel(new ClientTableModel());
    }
}
