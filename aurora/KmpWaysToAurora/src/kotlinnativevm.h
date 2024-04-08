#ifndef KOTLINNATIVEVM_H
#define KOTLINNATIVEVM_H

#include <QObject>
#include <QDebug>
#include <QQuickItem>

#include <libshared_api.h>

class KotlinNativeVM : public QObject
{
    Q_OBJECT

public:
    explicit KotlinNativeVM(QObject *parent = nullptr): QObject(parent) { qDebug(); };
    ~KotlinNativeVM() { qDebug(); }

    Q_INVOKABLE QString text() {
        auto ktText = libshared_symbols()->kotlin.root.platform();
        QString str = QString(ktText);

        auto ktDataClass = libshared_symbols()->kotlin.root.getDataClass();
        auto ktDataClassInt = libshared_symbols()->kotlin.root.DataClass.get_int(ktDataClass);
        auto ktDataClassStr = libshared_symbols()->kotlin.root.DataClass.get_string(ktDataClass);
        qDebug() << "Int: " << ktDataClassInt;
        qDebug() << "String: " << ktDataClassStr;

        auto dk = libshared_symbols()->kotlin.root.DataClass.DataClass(11, "pam");
        ktDataClassInt = libshared_symbols()->kotlin.root.DataClass.get_int(dk);
        ktDataClassStr = libshared_symbols()->kotlin.root.DataClass.get_string(dk);

        qDebug() << "Int: " << ktDataClassInt;
        qDebug() << "String: " << ktDataClassStr;

        return QString("Hello, %1").arg(str);
    }

//    Q_INVOKABLE void next() { emit nextPressed(m_counter); }

signals:
//    void counterChanged(int counter);
//    void nextPressed(int);

public slots:
//    void decreased(int counter) { setCounter(counter); }
};

#endif // KOTLINNATIVEVM_H
