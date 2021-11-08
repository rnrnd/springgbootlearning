package com.example.demo.agent;

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class Agent {

    public static void premain(String arg, Instrumentation instrumentation) {
        ClassPool pool = new ClassPool();
        pool.appendSystemPath();
        instrumentation.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                try {
                    className = className.replaceAll("/", ".");
                    CtClass ctClass = pool.get(className);
                    // 获取它所有方法
                    for (CtMethod declaredMethod : ctClass.getDeclaredMethods()) {
                        newMethod(declaredMethod)   ;
                    }
                    return ctClass.toBytecode();
                } catch (NotFoundException | CannotCompileException | IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }
    private static CtMethod newMethod(CtMethod oldMethod) throws CannotCompileException, NotFoundException {
        CtMethod copy = CtNewMethod.copy(oldMethod, oldMethod.getDeclaringClass(), null);
        copy.setName(oldMethod.getName() + "$agent");
        oldMethod.getDeclaringClass().addMethod(copy);// 添加新方法
        if (oldMethod.getReturnType().equals(CtClass.voidType)) {
            oldMethod.setBody(String.format(voidSource, oldMethod.getName()));
        } else {
            oldMethod.setBody(String.format(source, oldMethod.getName()));
        }
        return copy;
    }
    final static String source =
              " {\n"
            + "     long begin = System.currentTimeMillis();"
            + "     Object result;\n"
            + "     try {\n"
            + "         result=($w)%s$agent($$);\n"
            + "     }finally{\n"
            + "         long end = System.currentTimeMillis();"
            + "         System.out.println(end - begin);"
            + "     }\n"
            + "     return ($r) result;\n"
            + " }\n";

    final static String voidSource =
              "{\n"
            + "     long begin = System.currentTimeMillis();"
            + "     try {\n"
            + "         %s$agent($$);\n"
            + "     }finally{\n"
            + "         long end = System.currentTimeMillis();"
            + "         System.out.println(end - begin);"
            + "     }\n"
            + "}\n";
}
