#include <jni.h>
#include <string>

#include "opencv2/core/core.hpp"

//extern "C" JNIEXPORT jstring JNICALL
//Java_mobi_zack_opencv01_MainActivity_stringFromJNI(
//        JNIEnv *env,
//        jobject /* this */) {
//    std::string hello = "Hello from C++";
//    return env->NewStringUTF(hello.c_str());
//}


using namespace cv;

extern "C" JNIEXPORT jintArray JNICALL
Java_mobi_zack_opencv01_MainActivity_turnToGrey(JNIEnv *env,
                                          jobject /* this */, jintArray buf, jint w, jint h) {
    jint *cbuf;
    cbuf = env->GetIntArrayElements(buf, JNI_FALSE );
//    if (cbuf == NULL) {
//        return 0;
//    }
//    Mat img(h,w,CV_8UC4);
//    Mat img2(Size(200,100),CV_8UC3,Scalar(0,255,0));//长100，宽200
    Mat imgData(h, w,CV_8UC4, cbuf);
    uchar* ptr = imgData.ptr(0);
    for(int i = 0; i < w*h; i ++){
        //计算公式：Y(亮度) = 0.299*R + 0.587*G + 0.114*B
        //对于一个int四字节，其彩色值存储方式为：BGRA
        uchar grayScale = static_cast<uchar>((int)(ptr[4 * i + 2] * 0.299 + ptr[4 * i + 1] * 0.587 + ptr[4 * i + 0] * 0.114));
        ptr[4*i+1] = grayScale;
        ptr[4*i+2] = grayScale;
        ptr[4*i+0] = grayScale;
    }
//
    int size = w * h;
    jintArray result = env->NewIntArray(size);
    env->SetIntArrayRegion(result, 0, size, cbuf);
    env->ReleaseIntArrayElements(buf, cbuf, 0);
    return result;
}