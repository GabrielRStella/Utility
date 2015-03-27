# Utility
Various utilities written in java.
A copy of LWJGL 2.9.1 (and its natives) is included for ease of use. They can be found in the [src/lwjgl](https://github.com/XyPhoGR/Utility/tree/master/src/lwjgl) folder.
Please note that all sections are WIP, and therefore subject to sudden, complete change (or deletion).

Miscellaneous Utilities
-----------
Various useful classes are included with these utilities. Mainly they are included for use in other parts of the project, though some are extra.

Gui System
-----------
The GUI system implemented in this project is based on the java swing API, with a few major changes:
  * The GUI system works inside of a window, and will not create windows.
  * The GUI system has no default rendering implementation. The manner of rendering each component can be determined by user-assigned styles.
  * I forgot what the last point was; I'll probably remember it eventually.
It is meant to be entirely designed by the user. On its own, it has no external dependencies.
The GUI system can be found in the [com.ralitski.util.gui](https://github.com/XyPhoGR/Utility/tree/master/src/com/ralitski/util/gui) folder.

L-System API
-----------

Math API
-----------

openGL API
-----------

SQL API
-----------
