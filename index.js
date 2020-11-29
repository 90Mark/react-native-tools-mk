import { NativeModules } from 'react-native'
var { RNToolsManager } = NativeModules

export {
  downloadApkAndroidOnly
}

function downloadApkAndroidOnly (url,name) {
  RNToolsManager.downloadApkAndroidOnly(url,name)
}

