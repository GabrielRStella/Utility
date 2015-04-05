package com.ralitski.util;

//eheheh
import static com.ralitski.util.OperatingSystem.SystemType.*;

/**
 * list of a bunch of possible os.name values:
 * 
 * 
 * os name   os arch   java version
Windows XP   x86   1.5.0_02
Windows XP   x86   1.5.0
Windows XP   x86   1.5.0_03
Windows XP   x86   1.4.2_06
Windows XP   x86   1.5.0_01
Windows XP   x86   1.4.2_05
Windows XP   x86   1.4.2_08
Windows XP   x86   1.6.0-ea
Windows XP   x86   1.4.2_03
Windows XP   x86   1.4.2_07
Windows XP   x86   1.4.2_02
Windows XP   x86   1.4.2
Windows XP   x86   1.4.2_04
Windows XP   x86   1.5.0-beta2
Windows XP   x86   1.4.1_02
Windows XP   x86   1.5.0-rc
Windows XP   x86   1.4.1
Windows XP   x86   1.4.0
Windows XP   x86   1.6.0
Windows XP   x86   1.4.2_01
Windows XP   x86   1.5.0_04
Windows XP   x86   1.5.0-beta
Windows XP   x86   1.4.1_01
Windows XP   x86   1.4.2_09
Windows XP   x86   1.5.0_05
Windows XP   x86   1.4.1_03
Windows XP   x86   1.6.0-beta
Windows XP   x86   1.6.0-rc
Windows XP   x86   1.4.2_10
Windows XP   x86   1.5.0_06
Windows XP   x86   1.4.0_03
Windows XP   x86   1.6.0-beta2
Windows XP   x86   1.4.2_11
Windows XP   x86   1.5.0_07
Windows 2003   x86   1.5.0_02
Windows 2003   x86   1.5.0
Windows 2003   x86   1.5.0_03
Windows 2003   x86   1.4.2_06
Windows 2003   x86   1.5.0_01
Windows 2003   x86   1.4.2_05
Windows 2003   x86   1.6.0-ea
Windows 2003   x86   1.4.2_07
Windows 2003   x86   1.4.2_04
Windows 2003   x86   1.5.0-rc
Windows 2003   x86   1.5.0_04
Windows 2003   x86   1.5.0-beta
Windows 2003   x86   1.5.0_05
Windows 2003   x86   1.6.0-rc
Windows 2003   x86   1.5.0_06
Windows 2003   x86   1.5.0_07
Linux   i386   1.5.0_02
Linux   i386   1.5.0
Linux   i386   1.5.0_03
Linux   i386   1.4.2_06
Linux   i386   1.5.0_01
Linux   i386   1.4.2_05
Linux   i386   1.4.2_08
Linux   i386   1.6.0-ea
Linux   i386   1.4.2_03
Linux   i386   1.4.2_07
Linux   i386   1.4.2_02
Linux   i386   1.4.2-beta
Linux   i386   1.4.2
Linux   i386   1.4.2_04
Linux   i386   1.4.2-01
Linux   i386   1.5.0-rc
Linux   i386   1.4.2-rc1
Linux   i386   1.5.0_04
Linux   i386   1.4.2_09
Linux   i386   1.5.0_05
Linux   i386   1.6.0-beta
Linux   i386   1.6.0-rc
Linux   i386   1.4.2_10
Linux   i386   1.5.0_06
Linux   i386   1.6.0-beta2
Linux   i386   1.4.2_11
Linux   i386   1.5.0_07
Linux   amd64   1.4.2-01
Linux   amd64   1.5.0_05
Windows 2000   x86   1.5.0_02
Windows 2000   x86   1.5.0
Windows 2000   x86   1.5.0_03
Windows 2000   x86   1.4.2_06
Windows 2000   x86   1.5.0_01
Windows 2000   x86   1.4.2_05
Windows 2000   x86   1.4.2_08
Windows 2000   x86   1.6.0-ea
Windows 2000   x86   1.4.2_03
Windows 2000   x86   1.4.2_07
Windows 2000   x86   1.4.2_02
Windows 2000   x86   1.4.2-beta
Windows 2000   x86   1.4.2
Windows 2000   x86   1.4.2_04
Windows 2000   x86   1.5.0-beta2
Windows 2000   x86   1.4.1_02
Windows 2000   x86   1.5.0-rc
Windows 2000   x86   1.4.1
Windows 2000   x86   1.4.2_01
Windows 2000   x86   1.5.0_04
Windows 2000   x86   1.4.2_09
Windows 2000   x86   1.5.0_05
Windows 2000   x86   1.6.0-beta
Windows 2000   x86   1.6.0-rc
Windows 2000   x86   1.4.2_10
Windows 2000   x86   1.5.0_06
Windows 2000   x86   1.6.0-beta2
Windows 2000   x86   1.5.0_07
Windows 2000   x86   1.4.1_07
Mac OS X   i386   1.5.0_05
Mac OS X   i386   1.5.0_06
Mac OS X   ppc   1.5.0_02
Mac OS X   ppc   1.4.2_05
Mac OS X   ppc   1.4.2_03
Mac OS X   ppc   1.4.2_07
Mac OS X   ppc   1.4.2_09
Mac OS X   ppc   1.5.0_05
Mac OS X   ppc   1.5.0_06
Windows 98   x86   1.5.0_03
Windows 98   x86   1.4.2_06
Windows 98   x86   1.5.0_01
Windows 98   x86   1.4.2_02
Windows 98   x86   1.4.0
Windows 98   x86   1.4.2_01
SunOS   x86   1.5.0_04
SunOS   x86   1.5.0_06
SunOS   sparc   1.5.0_02
SunOS   sparc   1.4.2_04
SunOS   sparc   1.5.0-beta2
SunOS   sparc   1.5.0_05
SunOS   sparc   1.5.0_06
FreeBSD   i386   1.4.2-p6
FreeBSD   i386   1.4.2-p7
Windows NT   x86   1.5.0_02
Windows NT   x86   1.5.0
Windows NT   x86   1.4.2_05
Windows NT   x86   1.4.2_08
Windows NT   x86   1.4.2_03
Windows Me   x86   1.5.0_04
Windows Me   x86   1.5.0_06

condensed list:
Windows XP
Windows 2003
Windows 7
Linux
Windows 2000
Mac OS X
Windows 98
SunOS
FreeBSD
Windows NT
Windows Me

 * @author ralitski
 *
 */

public enum OperatingSystem {

	Windows_NT(WINDOWS),
	Windows_Me(WINDOWS),
	Windows_XP(WINDOWS),
	Windows_98(WINDOWS),
	Windows_2000(WINDOWS),
	Windows_2003(WINDOWS),
	Windows_7(WINDOWS),
	Windows_8(WINDOWS),
	Mac_OS_X(MAC),
	Linux(LINUX),
	SunOS(SUN),
	FreeBSD(FREEBSD);
	
	private SystemType type;
	
	OperatingSystem(SystemType type) {
		this.type = type;
	}
	
	public SystemType getSystemType() {
		return type;
	}
	
	public static OperatingSystem getSystem() {
		return parseName(System.getProperty("os.name"));
	}
	
	public static OperatingSystem parseName(String name) {
		return valueOf(name.replaceAll(" ", "_"));
	}
	
	public static enum SystemType {
		WINDOWS("windows"),
		MAC("macosx"),
		LINUX("linux"),
		SUN("solaris"),
		FREEBSD("freebsd");
		
		private String path;
		
		SystemType(String path) {
			this.path = path;
		}

		public String getPath() {
			return path;
		}
	}
}
