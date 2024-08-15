//
// Created by zhangjian on 7/26/24.
//

#include "com_advantech_scannerwedgedemo_module_ComPortActivity.h"

#include <termios.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <cstring>
#include <cerrno>
#include "error.h"
#include <cstdio>

#include "android/log.h"
static const char *TAG="ComPort";
#define LOGI(fmt, args...) __android_log_print(ANDROID_LOG_INFO,  TAG, fmt, ##args)
#define LOGD(fmt, args...) __android_log_print(ANDROID_LOG_DEBUG, TAG, fmt, ##args)
#define LOGE(fmt, args...) __android_log_print(ANDROID_LOG_ERROR, TAG, fmt, ##args)

static speed_t getBaudrate(jint baudrate) {
    switch (baudrate) {
        case 0:
            return B0;
        case 50:
            return B50;
        case 75:
            return B75;
        case 110:
            return B110;
        case 134:
            return B134;
        case 150:
            return B150;
        case 200:
            return B200;
        case 300:
            return B300;
        case 600:
            return B600;
        case 1200:
            return B1200;
        case 1800:
            return B1800;
        case 2400:
            return B2400;
        case 4800:
            return B4800;
        case 9600:
            return B9600;
        case 19200:
            return B19200;
        case 38400:
            return B38400;
        case 57600:
            return B57600;
        case 115200:
            return B115200;
        case 230400:
            return B230400;
        case 460800:
            return B460800;
        case 500000:
            return B500000;
        case 576000:
            return B576000;
        case 921600:
            return B921600;
        case 1000000:
            return B1000000;
        case 1152000:
            return B1152000;
        case 1500000:
            return B1500000;
        case 2000000:
            return B2000000;
        case 2500000:
            return B2500000;
        case 3000000:
            return B3000000;
        case 3500000:
            return B3500000;
        case 4000000:
            return B4000000;
        default:
            return -1;
    }
}

extern "C"
JNIEXPORT jobject JNICALL
Java_com_advantech_scannerwedgedemo_module_ComPortActivity_open(JNIEnv *env, jobject clazz,
                                                                jstring path, jint baudrate,
                                                                jint flags) {
    // TODO: implement open()
    int fd;
    speed_t speed = getBaudrate(baudrate);

    if (speed == -1) {
        LOGE("speed error %u", speed);
        return nullptr;
    }

    jboolean is_copy;
    const char *path_utf = env->GetStringUTFChars(path, &is_copy);
    fd = open(path_utf, O_RDWR | flags);
    if (fd == -1) {
        perror("open");
        LOGE("Failed to open the fd : %s\n", strerror(errno));
        return nullptr;
    }

    struct termios cfg = { 0 };
    if (tcgetattr(fd, &cfg)) {
        close(fd);
        return nullptr;
    }

    cfmakeraw(&cfg);
    cfsetispeed(&cfg, speed);
    cfsetospeed(&cfg, speed);

    if (tcsetattr(fd, TCSANOW, &cfg)) {
        LOGE("tcsetattr error");
        close(fd);
        return nullptr;
    }

    tcflush(fd, TCIOFLUSH);

    jclass objectClass = env->FindClass("java/io/FileDescriptor");
    jmethodID methodId = env->GetMethodID(objectClass, "<init>", "()V");
    jfieldID fieldId = env->GetFieldID(objectClass, "descriptor", "I");
    jobject mFileDescriptor = env->NewObject(objectClass, methodId);
    env->SetIntField(mFileDescriptor, fieldId, (jint) fd);

    return mFileDescriptor;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_advantech_scannerwedgedemo_module_ComPortActivity_close(JNIEnv *env, jobject thiz,
                                                                 jobject fd) {
    // TODO: implement close()
    jclass objectClass = env->FindClass("java/io/FileDescriptor");
    jfieldID fieldId = env->GetFieldID(objectClass, "descriptor", "I");
    jint descriptor = env->GetIntField(fd, fieldId);
    tcflush(descriptor, TCIOFLUSH);
    close(descriptor);
}
