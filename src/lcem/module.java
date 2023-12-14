package lcem;

import jshdesktop.JDesktopFrame;
import terra.shell.launch.Launch;
import terra.shell.modules.ModuleEvent;
import terra.shell.modules.ModuleEvent.DummyEvent;
import terra.shell.utils.keys.Event;
import terra.shell.utils.system.EventListener;
import terra.shell.utils.system.EventManager;

public class module extends terra.shell.modules.Module {

	@Override
	public String getName() {
		return "luaEdit";
	}

	@Override
	public void run() {

		EventManager.registerListener(new EventListener() {

			@Override
			public void trigger(Event e) {
				if (e.getCreator().equals("jshdesktop_initcompletion")) {
					final JDesktopFrame mainFrame = (JDesktopFrame) e.getArgs()[0];
					log.log("LCEM Enabled");
					log.log("Registering commands...");
					LaunchLuaCodeEditorCommand luaEdit = new LaunchLuaCodeEditorCommand(mainFrame);
					Launch.registerCommand(luaEdit.getName(), luaEdit);
				}
			}

		}, "jshdesktop_initcompletion");
	}

	@Override
	public String getVersion() {
		return null;
	}

	@Override
	public String getAuthor() {
		return null;
	}

	@Override
	public String getOrg() {
		return null;
	}

	@Override
	public void onEnable() {

	}

	@Override
	public void trigger(Event event) {
	}

}
