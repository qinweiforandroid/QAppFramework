package com.qw.framework.ui.tools

import com.qw.framework.ui.BaseFragment
import java.io.Serializable

/**
 * Created by qinwei on 2024/1/29 10:47
 * email: qinwei_it@163.com
 */
class ContainerClazz(val title: String = "", val clazz: Class<out BaseFragment>) : Serializable