#ifndef KOTLINNATIVEVM_H
#define KOTLINNATIVEVM_H

#include <QObject>
#include <QDebug>
#include <QQuickItem>

#include <libshared_api.h>

#include <functional>

class KotlinNativeVM : public QObject
{
    Q_OBJECT
    Q_PROPERTY(QString text MEMBER m_text NOTIFY textChanged)

public:
    explicit KotlinNativeVM(QObject *parent = nullptr): QObject(parent) { qDebug(); };
    ~KotlinNativeVM() { qDebug(); }

public slots:
    void std() {
        auto klib = libshared_symbols()->kotlin.root;

        auto ktText = klib.platform();
        auto ktDataClass1 = klib.getDataClass();
        auto ktDataClass1Str = klib.DataClass.toString(ktDataClass1);

        auto ktDataClass2 = klib.DataClass.DataClass(
                    2,
                    "some aurora string"
                    );
        auto ktDataClass2Int = klib.DataClass.get_int(ktDataClass2);
        auto ktDataClass2Str = klib.DataClass.get_string(ktDataClass2);

        // No Capture lambda as a C Ptr to Func
        // https://adroit-things.com/programming/c-cpp/how-to-bind-lambda-to-function-pointer/
        struct res {
            const char * text;
        } result {""};
        auto noCapture = [](void * data) {
            auto pResult = reinterpret_cast<res *>(data);
            pResult->text = "Triggered from lambda on Aurora";
        };
        typedef void(*NormalFuncType)(void * );
        NormalFuncType noCaptureLambdaPtr = noCapture;
        klib.triggerLambdaCfptr((libshared_KNativePtr)noCaptureLambdaPtr, &result);
        // --------------------------------------------------------------------------

        updateText(QString("Hello, %1\n%2\nDataClass2\nint: %3\nstring: %4\nfromLambda: %5")
                           .arg(ktText)
                           .arg(ktDataClass1Str)
                           .arg(ktDataClass2Int)
                           .arg(ktDataClass2Str)
                           .arg(result.text));
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
        auto noCapture = [](void * data, const char * text, bool finished) {
            auto that = reinterpret_cast<KotlinNativeVM *>(data);
            that->updateText(text);
        };
        typedef void(*NormalFuncType)(void *, const char *, bool);
        NormalFuncType noCaptureLambdaPtr = noCapture;
        klib.triggerCoroutineCfptr(1000, (libshared_KNativePtr)noCaptureLambdaPtr, this);
    }

    void ktor() {
        auto klib = libshared_symbols()->kotlin.root;
        auto noCapture = [](void * data, const char * text, bool finished) {
            auto that = reinterpret_cast<KotlinNativeVM *>(data);
            that->updateText(text);
        };
        typedef void(*NormalFuncType)(void *, const char *, bool);
        NormalFuncType noCaptureLambdaPtr = noCapture;
        klib.getKtorIoWelcomePageAsTextCfptr((libshared_KNativePtr)noCaptureLambdaPtr, this);
    }

    void db() {
        auto klib = libshared_symbols()->kotlin.root;

        auto df = klib.DriverFactory.DriverFactory();
        auto str = klib.getProgrammersFromSqlDelight(df);
        updateText(str);
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
