#include <jni.h>
#include <string>
using namespace std;

//
// Created by Ridh on 06/01/2024.
//

extern "C"
JNIEXPORT jstring JNICALL
Java_com_onedive_passwordstore_utils_Const_getNativeEncryptKey(JNIEnv *env, jclass clazz) {
    const string key = "var_34 + var_1c";
    return env->NewStringUTF(key.c_str());
}