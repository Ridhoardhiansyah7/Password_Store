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

extern "C"
JNIEXPORT jstring JNICALL
Java_com_onedive_passwordstore_utils_Const_getUpdateJsonBinId(JNIEnv *env, jclass clazz) {
    const string id = "65ac9b0d1f5677401f223ca5";
    return env->NewStringUTF(id.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_onedive_passwordstore_utils_Const_getApiSecretKey(JNIEnv *env, jclass clazz) {
    const string key = "$2a$10$7AOP0ccNkK.vWV4jkdlk2.dMPkTYBbDF3FP9emi8MdGp6poMQ7omS";
    return env->NewStringUTF(key.c_str());
}