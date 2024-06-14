LOCAL_PATH := $(call my-dir)

# this section handles building the android_serialport_api (used for standard serial communications)
NDK_TOOLCHAIN_VERSION := 4.9
	# clears out any existing values
	include $(CLEAR_VARS)

	# tells ndk what version of android to build for 
	TARGET_PLATFORM := android-15

	# name of the module that will be output name will be libserialport_api.so if this value is serialport_api
	LOCAL_MODULE    := serialport_api

	# tells the compilier where to find the source files
	LOCAL_SRC_FILES := SerialPort.c

	# tells the NDK builder to inclue the android logging wrappers
	LOCAL_LDLIBS    := -llog

	# tells ndk to add the shared lib to the project 
	include $(BUILD_SHARED_LIBRARY)
 
# end of building the android_serialport_api