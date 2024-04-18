#ifndef KOTLINNATIVEVM_H
#define KOTLINNATIVEVM_H

#include <QObject>
#include <QDebug>
#include <QQuickItem>

#include <libshared_api.h>

#include <functional>

class KtTriggerLambda {
public:
    static void capture(const char ** captureArg1);
    static void callback();
private:
    static const char ** captureArg1;
};

class KotlinNativeVM : public QObject
{
    Q_OBJECT
    Q_PROPERTY(QString text MEMBER m_text NOTIFY textChanged)

public:
    explicit KotlinNativeVM(QObject *parent = nullptr): QObject(parent) { qDebug(); };
    ~KotlinNativeVM() { qDebug(); }

public slots:
    void std() {
        auto ktText = libshared_symbols()->kotlin.root.platform();
        auto ktDataClass1 = libshared_symbols()->kotlin.root.getDataClass();
        auto ktDataClass1Str = libshared_symbols()->kotlin.root.DataClass.toString(ktDataClass1);

        auto ktDataClass2 = libshared_symbols()->kotlin.root.DataClass.DataClass(
                    2,
                    "some aurora string"
                    );
        auto ktDataClass2Int = libshared_symbols()->kotlin.root.DataClass.get_int(ktDataClass2);
        auto ktDataClass2Str = libshared_symbols()->kotlin.root.DataClass.get_string(ktDataClass2);

        auto lib = libshared_symbols();

        // Almost plain C-style: using static members of custom class KtTriggerLambda
        const char * result = "";
        KtTriggerLambda::capture(&result);
        auto kCallback = lib->kotlin.root.cfptrToFunc0((libshared_KNativePtr)KtTriggerLambda::callback);
        lib->kotlin.root.triggerLambda(kCallback);
        // --------------------------------------------------------------------------

        // No Capture lambda as a C Ptr to Func
        // https://adroit-things.com/programming/c-cpp/how-to-bind-lambda-to-function-pointer/
//        const char * result = "nothing";
//        auto noCapture = []() {
//            qDebug() << "No capture lambda called";
//            return;
//        };
//        typedef void(*NormalFuncType)();
//        NormalFuncType noCaptureLambdaPtr = noCapture;
//        auto kCallback = lib->kotlin.root.cfptrToFunc0((libshared_KNativePtr)noCaptureLambdaPtr);
//        lib->kotlin.root.triggerLambda(kCallback);
        // --------------------------------------------------------------------------

        // Lambda with capture as a C Ptr to Func
        // https://adroit-things.com/programming/c-cpp/how-to-bind-lambda-to-function-pointer/
//        const char * result = "nothing";
//        auto capture = [&result]() {
//            result = "Triggered from lambda on Aurora";
//            return;
//        };
//        typedef decltype(capture) CaptureLambdaType;
//        typedef void(CaptureLambdaType::*CaptureFuncType)() const;
//        CaptureFuncType captureLambda = &CaptureLambdaType::operator();
////        (capture.*captureLambda)();
////        auto kCallback = lib->kotlin.root.cfptrToFunc0((libshared_KNativePtr)...);
////        lib->kotlin.root.triggerLambda(kCallback);
        // --------------------------------------------------------------------------

        updateText(QString("Hello, %1\n%2\nDataClass2\nint: %3\nstring: %4\nfromLambda: %5")
                           .arg(ktText)
                           .arg(ktDataClass1Str)
                           .arg(ktDataClass2Int)
                           .arg(ktDataClass2Str)
                           .arg(result));
    }

    void serialization() {
        auto lib = libshared_symbols();

        auto dc = lib->kotlin.root.getDataClass();
        QString log = "Original to string: ";
        log.append(lib->kotlin.root.DataClass.toString(dc));
        auto str = lib->kotlin.root.serializeToString(dc);
        log.append("\nSerialized: ");
        log.append(str);
        dc = lib->kotlin.root.deserializeFromString(str);
        log.append("\nDeserialized from string: ");
        log.append(lib->kotlin.root.DataClass.toString(dc));


        updateText(log);
    }

    void coroutines() {
        updateText("coroutines");
    }

    void ktor() {
        updateText("ktor");
    }

    void db() {
        updateText("db");
    }

signals:
    void textChanged(const QString &newText);

private:
    QString m_text = "";

    void updateText(const QString &newText) {
        m_text = newText;
        emit textChanged(m_text);
    }
};

#endif // KOTLINNATIVEVM_H
