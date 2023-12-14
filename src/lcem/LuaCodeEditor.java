package lcem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.rsyntaxtextarea.FileLocation;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextAreaEditorKit;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.TextEditorPane;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;

import jshdesktop.JDesktopFrame;
import jshdesktop.desktop.frame.BasicFrame;
import jshdesktop.desktop.frame.DialogFrame;
import jshdesktop.desktop.frame.DialogFrame.DialogType;
import jshdesktop.desktop.frame.utilities.filemanager.InternalFileChooser;
import terra.shell.logging.LogManager;
import terra.shell.logging.Logger;

public class LuaCodeEditor extends BasicFrame {
	private TextEditorPane area;
	private RTextScrollPane editorScroller;
	private BasicFrame thisFrame = this;
	private JDesktopFrame mainFrame;
	private final File luaFile;
	private Logger log;

	public LuaCodeEditor(File luaCode, JDesktopFrame mainFrame) {
		super(mainFrame);
		this.luaFile = luaCode;
		this.mainFrame = mainFrame;
	}

	@Override
	public void create() {
		log = LogManager.getLogger("LuaEditorWindow");
		log.debug("Creating frame...");
		thisFrame = this;
		setTitle(luaFile.getName());

		area = new TextEditorPane();
		JPanel editor = new JPanel(new BorderLayout());

		try {
			area.load(FileLocation.create(luaFile));
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		area.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LUA);
		area.setCodeFoldingEnabled(true);
		area.setCaretPosition(0);

		try {
			Theme theme = Theme.load(getClass().getResourceAsStream("/org/fife/ui/rsyntaxtextarea/themes/dark.xml"));
			theme.apply(area);
		} catch (IOException ioe) { // Never happens
			ioe.printStackTrace();
		}

		area.setBracketMatchingEnabled(true);
		area.setMarkOccurrences(true);

		DefaultCompletionProvider cp = new DefaultCompletionProvider();
		cp.setAutoActivationRules(false, ".:");

		// TODO AutoCompletion

		editorScroller = new RTextScrollPane(area);

		registerKeyboardAction(new RSyntaxTextAreaEditorKit.IncreaseFontSizeAction(),
				KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS,
						Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx() | java.awt.Event.SHIFT_MASK),
				WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

		registerKeyboardAction(new RSyntaxTextAreaEditorKit.DecreaseFontSizeAction(),
				KeyStroke.getKeyStroke(KeyEvent.VK_MINUS,
						Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx() | java.awt.Event.SHIFT_MASK),
				WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

		setSize(600, 600);
		editorScroller.setPreferredSize(new Dimension(600, 600));
		editor.setPreferredSize(new Dimension(600, 600));

		editor.add(editorScroller);
		setContentPane(editor);
		setMaximizable(true);
		setJMenuBar(createMenuBar());
		pack();
		finish();
		log.debug("Finished");
	}

	private JMenuBar createMenuBar() {
		JMenuBar menu = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenuItem saveItem = new JMenuItem("Save");
		JMenuItem loadItem = new JMenuItem("Load");

		saveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					area.save();
				} catch (Exception e1) {
					DialogFrame.createDialog(DialogType.OK, "Error in saving: " + e1.getMessage());
				}
			}
		});

		loadItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InternalFileChooser chooser = new InternalFileChooser();
				chooser.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e2) {
						File selectedFile = chooser.getSelectedFile();
						new LuaCodeEditor(selectedFile, mainFrame);
					}
				});
			}
		});

		fileMenu.add(saveItem);
		fileMenu.add(loadItem);

		menu.add(fileMenu);
		return menu;
	}

}
