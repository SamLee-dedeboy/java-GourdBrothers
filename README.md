# 葫芦娃大作业
### 使用说明：
- 游戏共有8关，每关对应怪物阵营的一个阵型，所有关卡通过后游戏胜利
- 不需要按顺序通关
- 选择一个关卡，点击 StartRound 按钮开始游戏，葫芦娃自动摆好长蛇阵保卫爷爷。 怪物阵营会逐步向葫芦娃们靠近，进入技能射程后葫芦娃会使用技能攻击怪物。怪物的血量低于 0 后会留下一个尸体，怪物如果遇到尸体会踩着尸体前进（因为怪物很邪恶）。
- 如果在怪物没死完之前就点击 EndRound 按钮，则本关判定失败，需要重打。已通关的关卡按钮会失效。若爷爷死亡则本关失败。
- 爷爷会在后面加油助威，以一定概率发动技能：激励葫芦娃。技能发动时葫芦娃的攻击速度得到显著提升。
- 每结束一个关卡会在resource目录下生成一个日志。config.txt中记录了第几次运行日志系统。
- 初始界面：
![avator](https://github.com/SamLee-dedeboy/picturesURL/blob/master/InitialUI.jpg?raw=true)
- 怪物摆出Snake阵型:
![avator](https://github.com/SamLee-dedeboy/picturesURL/blob/master/Snake.jpg?raw=true)
- 使用技能：
![avator](https://github.com/SamLee-dedeboy/picturesURL/blob/master/SKILLs.jpg?raw=true)
- 通关：
![avator](https://github.com/SamLee-dedeboy/picturesURL/blob/master/Clear.jpg?raw=true)
- 游戏结束：
![avator](https://github.com/SamLee-dedeboy/picturesURL/blob/master/GAMEOVER.jpg?raw=true)
### 实现说明：
#### 1. Organism: 
- 所有生物体的父类，包括Minion, Serpent, Grandpa, Gourd, Scorpion。生物体有一些基本属性和动作，比如名字，位置，阵营，生命值，移动，使用技能。
- Organism实现Runnable接口，所以所有生物都是一个线程。在线程的生命周期中移动，使用技能，死亡后线程结束。
![avator](https://github.com/SamLee-dedeboy/picturesURL/blob/master/Beings.jpg?raw=true)
#### 2. Battlefield:
-   战场是一个14 x 10的二维矩阵，矩阵的每一项代表一个Block，生物和尸体都只能存在于Block上。每个Block也只能存放一个生物。
#### 3. Group: 
-   Monster和Hero的抽象父类，Group实现了Formation接口，所以每个阵营都需要实现Formation中的各个阵型函数。定义Group主要是为了多态地使用Monster和Hero
-   Hero 阵营：包含七个葫芦娃和爷爷，且只能通过 Hero 来访问
-   Monster 阵营：包含蛇蝎精和数量可变的喽啰，且只能通过 Monste 来访问
![avator](https://github.com/SamLee-dedeboy/picturesURL/blob/master/Collections.jpg?raw=true)
#### 4. Skill
- 实现技能的释放，定义技能的攻击力，范围，图像等。此外，Skill中定义了一个内部类FlyingSkill， 是一种特殊的Skill。每个FlyingSkill对象都被实现为一个线程。在生命周期内不断前进，直到遇到敌人，对其造成伤害才消亡。
![avator](https://github.com/SamLee-dedeboy/picturesURL/blob/master/Skill.jpg?raw=true)
#### 5. Controllers:
-   GUIController： 负责按钮的响应以及绘图板的绘制。同时也是fxml的Controller，实现Initializable接口，由fxmlLoader自动调用initialize函数，初始化GUI部件。此外，initialize函数会调用GameController的init函数，进行游戏内容的初始化。
-   GameController： 对游戏的变化做出响应。由于需要监听游戏中触发的事件，所以需要实现为一个线程（实际上是主线程），不断地等待如开始回合，结束回合事件的触发。一开始由于没有意识到GameController应该实现为一个线程，导致了许多的bug，可以说是这次作业遇到的最大困难。
-   LogController： 负责日志文件的读写。
![avator](https://github.com/SamLee-dedeboy/picturesURL/blob/master/Controller.jpg?raw=true)