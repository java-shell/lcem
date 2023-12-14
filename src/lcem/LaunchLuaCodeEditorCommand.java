package lcem;

import java.io.File;
import java.util.ArrayList;

import jshdesktop.JDesktopFrame;
import terra.shell.command.BasicCommand;
import terra.shell.utils.perms.Permissions;

public class LaunchLuaCodeEditorCommand extends BasicCommand {
	private final JDesktopFrame mainFrame;

	public LaunchLuaCodeEditorCommand(JDesktopFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	@Override
	public String getName() {
		return "luaEdit";
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAuthor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isBlocking() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<String> getAliases() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Permissions> getPerms() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean start() {
		if (args.length < 1) {
			return false;
		}
		String filePath = this.args[0];
		try {
			new LuaCodeEditor(new File(filePath), mainFrame);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}
