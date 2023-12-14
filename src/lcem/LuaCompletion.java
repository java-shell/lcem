package lcem;

import java.util.Hashtable;

import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;

import com.hk.lua.LuaLibrary;

import terra.shell.utils.lua.LuaHookManager;

public class LuaCompletion {

	public static CompletionProvider createLuaCompletion() {
		DefaultCompletionProvider cp = new DefaultCompletionProvider();
		return null;
	}

	private static CompletionProvider createCodeCP() {
		Hashtable<String, LuaLibrary<?>> libs = LuaHookManager.getAllLibs();
		return null;
	}

}
