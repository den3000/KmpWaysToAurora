#ifndef KOTLINNATIVEVM_H
#define KOTLINNATIVEVM_H

#include <QObject>
#include <QDebug>
#include <QQuickItem>

#include <libshared_api.h>
#include <desktop.h>

#include <functional>

class KotlinNativeVM : public QObject
{
    struct TestOneRes {
        KotlinNativeVM * that;
        const char * text;
        libshared_kref_kotlinx_datetime_Instant start;
    } testOneRes;

    struct TestTwoRes {
        KotlinNativeVM * that;
        const char * text;
        libshared_kref_kotlinx_datetime_Instant start;
    } testTwoRes;

    Q_OBJECT
    Q_PROPERTY(QString text MEMBER m_text NOTIFY textChanged)

public:
    explicit KotlinNativeVM(QObject *parent = nullptr): QObject(parent) { qDebug(); };
    ~KotlinNativeVM() { qDebug(); }

public slots:
    void std() {

        graal_isolate_t *isolate = NULL;
        graal_isolatethread_t *thread = NULL;

        if (graal_create_isolate(NULL, &isolate, &thread) != 0) {
            qDebug() << stderr << "initialization error\n";
        }

        qDebug() << "Platform: " << (char *)platform(thread);

        graal_tear_down_isolate(thread);

//        auto klib = libshared_symbols()->kotlin.root;

//        auto ktText = klib.platform();
//        auto ktDataClass1 = klib.getDataClass();
//        auto ktDataClass1Str = klib.DataClass.toString(ktDataClass1);

//        auto ktDataClass2 = klib.DataClass.DataClass(
//                    2,
//                    "some aurora string"
//                    );
//        auto ktDataClass2Int = klib.DataClass.get_int(ktDataClass2);
//        auto ktDataClass2Str = klib.DataClass.get_string(ktDataClass2);

//        // No Capture lambda as a C Ptr to Func
//        // https://adroit-things.com/programming/c-cpp/how-to-bind-lambda-to-function-pointer/
//        struct res {
//            const char * text;
//        } result {""};
//        auto noCapture = [](void * data) {
//            auto pResult = reinterpret_cast<res *>(data);
//            pResult->text = "Triggered from lambda on Aurora";
//        };
//        typedef void(*NormalFuncType)(void * );
//        NormalFuncType noCaptureLambdaPtr = noCapture;
//        klib.triggerLambdaCfptr((libshared_KNativePtr)noCaptureLambdaPtr, &result);
//        // --------------------------------------------------------------------------

//        updateText(QString("Hello, %1\n%2\nDataClass2\nint: %3\nstring: %4\nfromLambda: %5")
//                           .arg(ktText)
//                           .arg(ktDataClass1Str)
//                           .arg(ktDataClass2Int)
//                           .arg(ktDataClass2Str)
//                           .arg(result.text));
    }

    void serialization() {
        auto klib = libshared_symbols()->kotlin.root;

        auto dc = klib.getDataClass();
        QString log = "Original to string: ";
        log.append(klib.DataClass.toString(dc));
        auto str = klib.serializeToString(dc);
        log.append("\nSerialized: ");
        log.append(str);
        dc = klib.deserializeFromString(str);
        log.append("\nDeserialized from string: ");
        log.append(klib.DataClass.toString(dc));

        updateText(log);
    }

    void coroutines() {
        auto klib = libshared_symbols()->kotlin.root;
        auto noCapture = [](void * data, const char * text) {
            auto that = reinterpret_cast<KotlinNativeVM *>(data);
            that->updateText(text);
        };
        typedef void(*NormalFuncType)(void *, const char *);
        NormalFuncType noCaptureLambdaPtr = noCapture;

        updateText("Coroutine started");
        klib.triggerCoroutineCfptr(1000, (libshared_KNativePtr)noCaptureLambdaPtr, this);
    }

    void flow() {
        auto klib = libshared_symbols()->kotlin.root;
        auto noCapture = [](void * data, const char * text) {
            auto that = reinterpret_cast<KotlinNativeVM *>(data);
            that->updateText(text);
        };
        typedef void(*NormalFuncType)(void *, const char *);
        NormalFuncType noCaptureLambdaPtr = noCapture;

        updateText("Flow started");
        klib.triggerFlowCfptr(1000, (libshared_KNativePtr)noCaptureLambdaPtr, this);
    }

    void ktor() {
        auto klib = libshared_symbols()->kotlin.root;
        auto noCapture = [](void * data, const char * text) {
            auto that = reinterpret_cast<KotlinNativeVM *>(data);
            that->updateText(text);
        };
        typedef void(*NormalFuncType)(void *, const char *);
        NormalFuncType noCaptureLambdaPtr = noCapture;

        updateText("Request started");
        klib.getKtorIoWelcomePageAsTextCfptr((libshared_KNativePtr)noCaptureLambdaPtr, this);
    }

    void db() {
        auto klib = libshared_symbols()->kotlin.root;

        auto noCapture = [](void * data, const char * text) {
            auto that = reinterpret_cast<KotlinNativeVM *>(data);

            that->appendText(text);
        };
        typedef void(*NormalFuncType)(void *, const char *);
        NormalFuncType noCaptureLambdaPtr = noCapture;

        updateText("DB started");
        auto df = klib.DriverFactory.DriverFactory();
        klib.getProgrammersFromSqlDelightCfptr(df, (libshared_KNativePtr)noCaptureLambdaPtr, this);
    }

    void test1() {
        auto klib = libshared_symbols()->kotlin.root;

        testOneRes = {this, "", klib.getTimeMark()};
        auto df = klib.DriverFactory.DriverFactory();

        auto noCapture = [](void * data, const char * text) {
            auto res = reinterpret_cast<TestOneRes *>(data);
            auto totalTime = libshared_symbols()->kotlin.root.getDiffMs(res->start);
            res->that->updateText(QString("Time spent: %1 ms\n%2")
                                  .arg(totalTime)
                                  .arg(text));

        };
        typedef void(*NormalFuncType)(void *, const char *);
        NormalFuncType noCaptureLambdaPtr = noCapture;

        updateText("Test 1 started");
        klib.runTestOneCfptr(df, (libshared_KNativePtr)noCaptureLambdaPtr, &testOneRes);
    }

    void test2() {
        auto klib = libshared_symbols()->kotlin.root;

        testTwoRes = {this, "", klib.getTimeMark()};
        auto df = klib.DriverFactory.DriverFactory();

        auto noCapture1 = [](void * data) {
            auto res = reinterpret_cast<TestTwoRes *>(data);
            res->start = libshared_symbols()->kotlin.root.getTimeMark();
        };
        typedef void(*NormalFuncType1)(void *);
        NormalFuncType1 noCaptureLambdaPtr1 = noCapture1;

        auto noCapture2 = [](void * data, const char * text) {
            auto res = reinterpret_cast<TestTwoRes *>(data);
            auto totalTime = libshared_symbols()->kotlin.root.getDiffMs(res->start);
            res->that->updateText(QString("Time spent: %1 ms\n%2")
                                  .arg(totalTime)
                                  .arg(text));
        };
        typedef void(*NormalFuncType2)(void *, const char *);
        NormalFuncType2 noCaptureLambdaPtr2 = noCapture2;

        updateText("Test 2 started");
        klib.runTestTwoCfptr(
                    df,
                    (libshared_KNativePtr)noCaptureLambdaPtr1,
                    (libshared_KNativePtr)noCaptureLambdaPtr2,
                    &testTwoRes
                    );
    }

signals:
    void textChanged(const QString &newText);

private:
    QString m_text = "";

    void updateText(const QString &newText) {
        m_text = newText;
        emit textChanged(m_text);
    }

    void appendText(const QString &newText) {
        m_text.append(newText);
        emit textChanged(m_text);
    }
};

#endif // KOTLINNATIVEVM_H
