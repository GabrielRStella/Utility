package com.ralitski.util.render;

import com.ralitski.util.JarExtractor;
import com.ralitski.util.OperatingSystem;
import com.ralitski.util.OperatingSystem.SystemType;

public class GLExtract {
	
	public static void extractLWJGL(String jarFile) {
		extractLWJGL(jarFile, OperatingSystem.getSystem().getSystemType());
	}
	
	public static void extractLWJGL(String jarFile, SystemType type) {
		extractLWJGL(jarFile, "./", "./natives/", OperatingSystem.getSystem().getSystemType());
	}
	
	public static void extractLWJGL(String jarFile, String jarDest, String nativeDest) {
		extractLWJGL(jarFile, jarDest, nativeDest, OperatingSystem.getSystem().getSystemType());
	}
	
	public static void extractLWJGL(String jarFile, String jarDest, String nativeDest, SystemType type) {
		String jarPathInJar = "lwjgl/";
		JarExtractor extractJar = new JarExtractor(jarFile, jarPathInJar, jarDest, ".jar");
		String nativePathInJar = "lwjgl/native/" + type.getPath() + "/";
		JarExtractor extractNative = new JarExtractor(jarFile, nativePathInJar, nativeDest, null);
		extractJar.extractJar();
		extractNative.extractJar();
		System.setProperty("java.library.path", nativeDest);
	}
}
