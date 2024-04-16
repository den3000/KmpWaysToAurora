#ifndef KOTLINNATIVEVM_H
#define KOTLINNATIVEVM_H

#include <QObject>
#include <QDebug>
#include <QQuickItem>

#include <libshared_api.h>

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

        updateText(QString("Hello, %1\n%2\nDataClass2\nint: %3\nstring: %4")
                           .arg(ktText)
                           .arg(ktDataClass1Str)
                           .arg(ktDataClass2Int)
                           .arg(ktDataClass2Str));
    }

    void serialization() {
        updateText("serialization");
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
