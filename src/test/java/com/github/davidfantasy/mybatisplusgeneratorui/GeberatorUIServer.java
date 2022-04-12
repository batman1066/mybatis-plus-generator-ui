package com.github.davidfantasy.mybatisplusgeneratorui;

import cn.hutool.core.util.StrUtil;
import com.github.davidfantasy.mybatisplus.generatorui.GeneratorConfig;
import com.github.davidfantasy.mybatisplus.generatorui.MybatisPlusToolsApplication;
import com.github.davidfantasy.mybatisplus.generatorui.dto.Constant;
import com.github.davidfantasy.mybatisplus.generatorui.mbp.NameConverter;
import com.google.common.base.Strings;

import static com.github.davidfantasy.mybatisplus.generatorui.dto.Constant.DOT_JAVA;
import static com.github.davidfantasy.mybatisplus.generatorui.dto.Constant.DOT_XML;

/**
 * GeberatorUIServer
 *
 * @author yangming
 * @date 2022-04-12 09:48:40
 */
public class GeberatorUIServer {
    public static void main(String[] args) {
        GeneratorConfig config = GeneratorConfig.builder().jdbcUrl("jdbc:mysql://192.168.110.139:3306/meapay?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false")
                .userName("root")
                .password("111111")
                .driverClassName("com.mysql.jdbc.Driver")
                //数据库schema，POSTGRE_SQL,ORACLE,DB2类型的数据库需要指定
                //.schemaName("meapay")
                //如果需要修改各类生成文件的默认命名规则，可自定义一个NameConverter实例，覆盖相应的名称转换方法：
                .nameConverter(new NameConverter() {

                    /**
                     * 自定义Entity.java的类名称
                     *
                     * @param tableName 表名称
                     * @return
                     */
                    @Override
                    public  String entityNameConvert(String tableName) {
                        return entityNameConvertReal(tableName)+"DO";
                    }
                    public  String entityNameConvertReal(String tableName) {
                        if (Strings.isNullOrEmpty(tableName)) {
                            return "";
                        }
                        tableName = tableName.substring(tableName.indexOf(StrUtil.UNDERLINE) + 1, tableName.length());
                        return StrUtil.upperFirst(StrUtil.toCamelCase(tableName.toLowerCase()));
                    }

                    /**
                     * 自定义表字段名到实体类属性名的转换规则
                     *
                     * @param fieldName 表字段名称
                     * @return
                     */
                    @Override
                    public  String propertyNameConvert(String fieldName) {
                        if (Strings.isNullOrEmpty(fieldName)) {
                            return "";
                        }
                        if (fieldName.contains("_")) {
                            return StrUtil.toCamelCase(fieldName.toLowerCase());
                        }
                        return fieldName;
                    }

                    /**
                     * 自定义Mapper.java的类名称
                     */
                    @Override
                    public  String mapperNameConvert(String tableName) {
                        return entityNameConvertReal(tableName) + "Mapper";
                    }

                    /**
                     * 自定义Mapper.xml的文件名称
                     */
                    @Override
                    public  String mapperXmlNameConvert(String tableName) {
                        return entityNameConvertReal(tableName) + "Mapper";
                    }

                    /**
                     * 自定义Service.java的类名称
                     */
                    @Override
                    public  String serviceNameConvert(String tableName) {
                        return "I" + entityNameConvertReal(tableName) + "Service";
                    }

                    /**
                     * 自定义ServiceImpl.java的类名称
                     */
                    @Override
                    public  String serviceImplNameConvert(String tableName) {
                        return entityNameConvertReal(tableName) + "ServiceImpl";
                    }

                    /**
                     * 自定义Controller.java的类名称
                     */
                    @Override
                    public  String controllerNameConvert(String tableName) {
                        return entityNameConvertReal(tableName) + "Controller";
                    }


                })
                .basePackage("com.mea.pay.mtn")
                .port(8068)
                .build();
        MybatisPlusToolsApplication.run(config);
    }


}
