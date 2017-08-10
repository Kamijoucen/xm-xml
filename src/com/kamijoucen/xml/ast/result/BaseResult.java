package com.kamijoucen.xml.ast.result;

import com.kamijoucen.xml.token.TokenLocation;

import java.util.List;

/**
 * 需要定义两个接口， Ast    (抽象结构树，表示 val 的集合，不需要包含 tokenLocation 位置信息)
 *                   Result (结果      ，表示
 *
 */

/**
 * 对于结果的抽象
 */
public interface BaseResult {
    String val();
}
