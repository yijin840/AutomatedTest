# 基于swagger框架自动化测试脚本

自动化脚本分为三部分

- 配置文件 config.yml 和 json参数配置文件
- 构建器
- 测试器

## 构建

build方法， 默认生成一份 test.json 文件，格式如下:

```json
{
	"/executeTask":{
		"headers":{},
		"params":{},
		"result":{},
		"type":"get"
	},
	"/testPool":{
		"headers":{},
		"params":{},
		"result":{},
		"type":"get"
	}
}
```

主要信息：

- 接口路径
- 头信息
- 参数信息
- 结果信息
- type类型（此类型由config.yml中配置）


## 测试

测试基于HttpClient， 自动根据参数和接口类型发送参数进行结果校验