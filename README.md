大二时候学安卓的时候做的一款游戏，魔塔是一款RPG（人物扮演）策略游戏，这里面我加入了当下流行的游戏王者荣耀的一些元素：闪现、斩杀
魔塔一次只能移动一格，遇到怪物自动战斗，遇到宝箱自动捡取
战斗公式:回合制:我方耗血量=敌方怪物攻击-英雄防御   敌方耗血量＝英雄攻击－敌方怪物防御   伤害计算中规中矩，打不过怪物则不会进行战斗。
小特点:加了两个技能，一个闪现，一个斩杀闪现可以向当前方向瞬移两格，没钥匙时可用来穿墙，打不过怪物时也可以抄近路避开怪物。
斩杀则可以消灭该层上非BOSS级的怪物，但是不会得到金币和经验，有利有弊吧。
人物被动技能:每100HP加攻击一点
龙被动技能:每100HP加防御一点
双英雄形态，最好一个主力，一个辅助，不然属性太平均最后只能是两个废物。。。
不足之处:战斗逻辑处理其实不难，但是具体碰到的怪物是哪个种类要一一判断，几十个怪物难道几十条判断语句吗？四个方向都去这样判断，
保守估计整个游戏完成3000行代码商店的位置有点小小的bug，改了几次还是没弄好，占了三个格子，但是只有碰到最右边的时候才有用，
碰左边和中间都等于碰墙，不这样设计的话图片不能正常显示
-----------分割线-----------
不足之处其实学过了设计模式之后已经懂得了怎么改进，要想避免大量的if语句，可以使用工厂模式，然后怪物的种类有些是差不多的，比如小蝙蝠、大蝙蝠、红蝙蝠
这里可以用装饰设计模式，在小蝙蝠的基础上增加新的特性以及图片、属性等的替换。设计模式6啊！！