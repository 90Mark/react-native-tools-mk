
import { downloadApkAndroidOnly } from 'react-native-tools-mk'

downloadApkAndroidOnly(url, 'download.apk')






AndroidManifest.xml

    <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/>

    <application
       ...
        android:requestLegacyExternalStorage="true"
        >


