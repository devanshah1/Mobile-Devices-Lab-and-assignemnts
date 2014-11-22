How to run the program 

Open up eclipse and import directory DevanSuperPhotoList included in the zip file. 
Please make sure that appcompat_v7 is also available in eclipse.
Please make sure that google-play-services_lib is also available in eclipse and setup as reference.

Please use the following device configuration to make use of the Google Play Services:
    Device: Nexus S (4.0", 480 x 800: hdpi)
    Target: Google APIs (Google Inc.) - API Level 19
    CPU/ABI: ARM (armeabi-v7a)
    Keyboard: Option selected.
    Skin: Skin with dynamic hardware controls
    Front Camera: Emulated
    Back Camera: Emulated
    Memory Options: RAW: 343  VM Heap: 32
    Internal Storage: 200 MiB
    SD Card
        Size: 500 MiB

    Rest of the devices options should not matter.

Please use the following device configuration to test out the rotations:
    Device: Nexus S (4.0", 480 x 800: hdpi)
    Target: Andriod 4.3.1 - API Level 18
    CPU/ABI: ARM (armeabi-v7a)
    Keyboard: Option selected.
    Skin: Skin with dynamic hardware controls
    Front Camera: Emulated
    Back Camera: Emulated
    Memory Options: RAW: 343  VM Heap: 32
    Internal Storage: 200 MiB
    SD Card
        Size: 500 MiB

    Rest of the devices options should not matter.

    The reason that need to use a different device to test rotaion is because API Level 19 emulator 
    does not support rotation, this is a bug in the emulator. Info on the bug can be found at:
    http://stackoverflow.com/questions/19726285/impossible-to-rotate-the-emulator-with-android-4-4

If further details are required please let me know and they can be provided. Thanks