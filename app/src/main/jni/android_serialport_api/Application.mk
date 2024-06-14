# this only builds the embedded version for newer arm CPU see http://stackoverflow.com/questions/7080525/why-use-armeabi-v7a-code-over-armeabi-code
APP_OPTIM := release
# this builds armebi = older cpu and x86 so it can run on emulators is is disabled currently
APP_ABI := armeabi-v7a

APP_STL := gnustl_static
APP_PLATFORM := android-15
NDK_DEBUG=1