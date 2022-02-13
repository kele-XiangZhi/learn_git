package com.example.spring01;

import lombok.Data;
import java.util.List;

/**
 * 设置一个包含的子类以来的方法  用于体现spring DI的控制反转
 * 正常的文件依赖是 创建一个包含下面的类，DepartmentB 需要把groupc的列表也创建
 * 但是因为有了spring的DI 就是把依赖关系给放在了contest中 在加载的时候 会把需要的依赖类从关系中找到 反响退给Department.
 * 而不是department 去找他之后再去进行创建。因为是解耦的关系，  涉及到spring bean之间的关系初始化。
 *
 */


@Data
public class DepartmentB {

    List<GroupC> groupCs;
    public void pang(){

        System.out.println(this.getGroupCs());

    }
}
