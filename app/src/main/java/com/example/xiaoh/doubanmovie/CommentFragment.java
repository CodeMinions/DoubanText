package com.example.xiaoh.doubanmovie;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;


public class CommentFragment extends Fragment {

    final int ResponseSuccessful = 1;

    private List<String> user_Name = new ArrayList<String>();
    private List<String> user_Image = new ArrayList<String>();
    private List<String> comment = new ArrayList<String>();
    private List<String> comment_Date = new ArrayList<String>();
    private List<String> comment_votes = new ArrayList<String>();


    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private CommentAdapter adapter;

    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.comment_fragment, container, false);

        // FIXME: 2018/8/22 关于nodejs在java中的使用，还是不解，问钊姐。先暂用
//        String data = "[{\"doubanId\":1404895919,\"name\":\"远去\",\"avatar\":\"https://img3.doubanio.com/icon/u157254767-1.jpg\",\"votes\":13809,\"text\":\"很奇怪，中国大陆的名导都是出道即巅峰，然后一路下坡，贾樟柯，张艺谋，陈凯歌，姜文，无一例外。这反映了内地电影圈的两个问题，新人出头难，老人死得慢。\\\\看不懂的朋友按自己观感给分就好了，别被裹挟着跟着瞎捧，万一捧到一坨翔就尴尬了。大导拍烂片，古已有之。\",\"time\":\"2018-07-13\",\"rate\":10},{\"doubanId\":1400160071,\"name\":\"金社长\",\"avatar\":\"https://img3.doubanio.com/icon/u59025385-6.jpg\",\"votes\":9912,\"text\":\"不要再问我邪不压正好不好看了哥哥姐姐们，反正朋友《一步之遥》看了10分钟就离场的这次字幕看完都舍不得走。                  药神太猛，我是姜文，我现在慌的一匹            补充：要说现实主义，药神绝对不是真正意义上的现实主义，对于一个已经解决了的事件、一个已经不是问题的问题拿出来说就已经违背了现实主义题材的核心––讨论“真”问题。我们当下所面临的问题是这个时代给我们带来的困境，是精神层面的疾病，是我们想解决但又都不敢去解决的，所以，我认为《邪不压正》才是真正具有现实主义的电影，只不过它是魔幻的现实主义，只不过大家被荒诞惯了，再也不相信真现实了\",\"time\":\"2018-07-13\",\"rate\":50},{\"doubanId\":1399296057,\"name\":\"海子的海\",\"avatar\":\"https://img3.doubanio.com/icon/u175421286-3.jpg\",\"votes\":5785,\"text\":\"彭于晏有民国风吗？不好意思我只觉得出戏。大清早亡了，还弄这浮夸的台词，浮夸的表演，姜文一贯自大狂式的卖弄让人作呕。\",\"time\":\"2018-07-14\",\"rate\":10},{\"doubanId\":1405081136,\"name\":\"亵渎电影\",\"avatar\":\"https://img3.doubanio.com/icon/u3995080-211.jpg\",\"votes\":5480,\"text\":\"姜文不止一次说，他的戏都是编剧到了现场还在改剧本，现在严重的问题来了！电影的大框架是纯商业类型片，剪辑方法也是，一开始情节推情节的主线节奏很快，但彭于晏饰演的男一号却没有主观能动性，无法驱动情节，只是一个棋子，也看不出来他内心的恐惧和性格的层次感，而躲子弹、筷子杀鬼子的身手又跟抗日神剧差不多，半小时之后这个剧本就烂成了筛子。节奏亢奋失控本来就是姜文的问题，这次的剧本更是凸显了这个问题，本来很简单的故事，但混乱的节奏却让人有难以消化的感觉，这就是很多人看完不舒服的原因。不要小看类型片，总想着反类型耍小聪明做自己，强类型和夹带私货很容易互相撕扯。\",\"time\":\"2018-07-13\",\"rate\":20},{\"doubanId\":1404926526,\"name\":\"姬长安\",\"avatar\":\"https://img3.doubanio.com/icon/u3551155-23.jpg\",\"votes\":5061,\"text\":\"导演粉丝电影和明星粉丝电影有什么区别？大概多了一件李天然的皇帝的艺术新衣罢了&原来站着挣钱的《让子弹飞》只是偶然喽，姜文到现在还没分清楚商业院线和艺术院线么？实现自我表达夹杂你那么多大院子弟高姿态的自以为是的隐喻就滚去艺术院线&世界上最贱的一批人是跪舔姜文的影评人\",\"time\":\"2018-07-13\",\"rate\":20},{\"doubanId\":1401305546,\"name\":\"影志\",\"avatar\":\"https://img1.doubanio.com/icon/u1005928-127.jpg\",\"votes\":4601,\"text\":\"“-不要再哭了，再哭就到了！-我压着速度呢”廖凡部分犀利狠辣如《无耻混蛋》，周韵部分仙逸飘渺如《聂隐娘》，彭于晏屋顶奔跑如《卧虎藏龙》复仇双刃剑如《三块广告牌》，许晴情义妖娆如《老炮儿》里的她和《太阳照常升起》里的陈冲…廖许边电话边啪啪、彭于晏屋顶裸奔、彭叫姜文爸爸…都极其情艳！\",\"time\":\"2018-07-07\",\"rate\":40},{\"doubanId\":1373550380,\"name\":\"汪金卫\",\"avatar\":\"https://img3.doubanio.com/icon/u4285566-30.jpg\",\"votes\":3585,\"text\":\"曾经很欣赏《一步之遥》，当年观感是惊喜的。其尚有迷影之元素，戏中戏之架构，《我这一辈子》之手法，浪漫地肆意妄为。能和《地狱为何恶劣》比一比。而《邪不压正》则让我大失所望，达不到园子温那般癫狂High翻，反倒是抗日神剧、闹剧路线：日本人一定玩阴的，中国少侠躲子弹一打十牛逼。针扎战书手雷互扔，设计低级无聊硬搞笑。以姜文水准不应该如此低智。姜文作品中个人观感最差的一部，不顾逻辑剧情，放飞自我恶趣味。细节粗糙，喜剧脱缰。满口京片子的配角Andy倒是很出彩。许晴负责卖骚，廖凡成丑角，周韵角色设定倒是很讨好。高潮动作戏日本军官简直变成《举起手来》的潘长江，只剩下蠢和弱。还以为要推翻剧情，师傅是李天然所杀，可惜没有。而对影评人冠以“太监、文盲”的直白作践更是小丑般胡闹，恶心莫名。相信彭于晏粉丝才会心满意足吧。\",\"time\":\"2018-07-11\",\"rate\":20},{\"doubanId\":1403501734,\"name\":\"翻滚吧！蛋堡\",\"avatar\":\"https://img3.doubanio.com/icon/u7542909-82.jpg\",\"votes\":3775,\"text\":\"3.5 大概算是姜文电影中直白又平庸的一部，属于导演个人恶趣味的东西反而成了双刃剑，时而灵光闪现，时而尴尬无趣。屋顶和医院戏观感佳，到后半程高潮节奏又莫名拖沓，没有之前作品那种酣畅淋漓的快感。\",\"time\":\"2018-07-10\",\"rate\":30},{\"doubanId\":1405085562,\"name\":\"忘\",\"avatar\":\"https://img3.doubanio.com/icon/u28932117-154.jpg\",\"votes\":3868,\"text\":\"我看的是《邪不压正》吗？我以为我看的是《彭于晏找爸爸》\",\"time\":\"2018-07-13\",\"rate\":10},{\"doubanId\":1404844062,\"name\":\"纹你\",\"avatar\":\"https://img3.doubanio.com/icon/u146221624-4.jpg\",\"votes\":3015,\"text\":\"3.5分 彭于晏很赶客 湾湾口音 持续不断的卖肉 以及被所有演员碾压的演技。姜文这一次发挥失常。一部电影的成功与否 选角也很重要 不对 是非常非常重要。\",\"time\":\"2018-07-12\",\"rate\":20},{\"doubanId\":1404945060,\"name\":\"仰望星空\",\"avatar\":\"https://img3.doubanio.com/icon/u142815078-2.jpg\",\"votes\":2908,\"text\":\"一部民国时期的跑酷，卖点是彭于晏的肌肉，演得特别别扭。姜文真是才华耗尽了。\",\"time\":\"2018-07-13\",\"rate\":10},{\"doubanId\":1405100012,\"name\":\"frozenmoon\",\"avatar\":\"https://img3.doubanio.com/icon/u1233038-2.jpg\",\"votes\":2728,\"text\":\"凌乱、浮夸、乱入私货，还觉得自己恣意挥洒；密集、无效、莫民奇妙的话痨台词，让人脑袋疼，总觉得有特殊的幽默感，实际上全程尴尬。姜文一勺烩了自己所有想法，包括恶趣味、偏见和价值观，没有轻重缓急，缺少取舍研判，在混乱之后还有个很鸡汤的治愈心病的结尾？没必要从细部刻意找象征，难看就是难看\",\"time\":\"2018-07-13\",\"rate\":20},{\"doubanId\":1404692491,\"name\":\"康斯坦\",\"avatar\":\"https://img3.doubanio.com/icon/u47435721-20.jpg\",\"votes\":2556,\"text\":\"因为姜文是个任性的孩子，观众们就要陪他一起装傻吗？？？？？什么鬼啊！！\",\"time\":\"2018-07-12\",\"rate\":20},{\"doubanId\":1403569060,\"name\":\"张三\",\"avatar\":\"https://img3.doubanio.com/icon/u48156155-4.jpg\",\"votes\":2143,\"text\":\"从周韵出来，影片就属于崩塌状了。李小龙闯入卡萨布兰卡，缺毫无紧张气氛。几处笑点不错，但是远没有达到姜文的水准。史航这个角色毫无存在的意义。 说实话，看的犯困，姜文是真爱自己的媳妇。\",\"time\":\"2018-07-10\",\"rate\":20},{\"doubanId\":1405069269,\"name\":\"MadRain\",\"avatar\":\"https://img1.doubanio.com/icon/user_normal.jpg\",\"votes\":2616,\"text\":\"不管是彭于晏的屁股，还是许晴的屁股，都没有成功的把我的屁股留在影院的座位上。\",\"time\":\"2018-07-13\",\"rate\":20},{\"doubanId\":1404878663,\"name\":\"UCcurlao\",\"avatar\":\"https://img3.doubanio.com/icon/u156748896-2.jpg\",\"votes\":2161,\"text\":\"崇尚性与暴力，充满直男癌大男子主义气息\",\"time\":\"2018-07-13\",\"rate\":10},{\"doubanId\":1318829013,\"name\":\"津五渡\",\"avatar\":\"https://img3.doubanio.com/icon/u51783252-22.jpg\",\"votes\":1919,\"text\":\"“北洋三部曲”各有各的悲伤，《邪不压正》悲伤最甚。第三部集前两部之大成。只看表层是“杀死比尔”一样的复仇故事，姜文多了些深情。内核里还是“北洋三部曲”特有的，关于政治与历史的隐喻，都是“怎么就变成这样了呢？”的思考。密集，讲究的台词依旧是我的大爱，兑上十杯水也能品出酒味。是姜文想象中的民国北京房顶上上演的戏剧，是骨子里的幽默与孤独，是邪不压正。\",\"time\":\"2018-07-12\",\"rate\":50},{\"doubanId\":1405503943,\"name\":\"西楼尘\",\"avatar\":\"https://img3.doubanio.com/icon/u49290419-24.jpg\",\"votes\":1899,\"text\":\"为吃醋做一顿饺子，为贺礼烧一座烟房，饺子没吃到嘴里，鸦片迷醉进鼻腔。为不老打一管针剂，为隐形做一件衣裳，不老却主动求死，隐形却光着皮囊。汉奸跪在地上如狗，侠客飞上屋檐如鸽，谁睡在丧钟里不醒，谁僵在火海里失声。躲得过爱情的子弹，拔不掉仇恨的齿牙，北平本是一座孤岛，海水退去便是江湖。\",\"time\":\"2018-07-14\",\"rate\":40},{\"doubanId\":1405153726,\"name\":\"桃桃淘电影\",\"avatar\":\"https://img3.doubanio.com/icon/u1032989-50.jpg\",\"votes\":1336,\"text\":\"7分吧，整体还是好过《一步之遥》。一部成也姜文、败也姜文的片子。强烈的个人风格，让影片已经跟原著《侠隐》成了两个东西。姜文的电影里，角色总是带着些疯癫，所有人都像嗨过一样，台词密，表演也有意的过。同时，角色又有他眼里的单纯与浪漫，是可爱的一面。节奏依然特别飞，还是非常厉害。而台词密度大到有些不适感，他开心就好吧。 想法太多，使得影片会有些散。另一方面，隐喻仍然很多，坏主意也仍然很多，可以注意廖凡越来越明显的下巴。喜欢姜文风格的人，还是不会太讨厌这部。不过，损影评人那个地方，还是很别扭。\",\"time\":\"2018-07-13\",\"rate\":40},{\"doubanId\":1405229168,\"name\":\"凌睿\",\"avatar\":\"https://img1.doubanio.com/icon/u63688511-18.jpg\",\"votes\":1559,\"text\":\"《邪不压正》表面上非常大男子主义，其实处处体现出对女性的尊重。关巧红和唐凤仪都是很女权的角色，关巧红拒绝缠足，尝试像普通人一样奔跑，坚决要和男人享有同等的地位，这是她自食其力、自力更生，尝试做一个独立新女性的体现；而唐凤仪当众扇朱潜龙耳光，后来又一言不合就甩了朱潜龙，大胆追求李天然而不在乎他人眼光。她的身体完全由自己的灵魂主宰，她完全是自己的主人，不是男人的附庸，也不做他人的小妾，她的女性自我意识已经全然觉醒。女性角色如此强硬、独立、自主的一部电影，怎能叫直男癌呢？姜文曾说过，他的电影里真正的英雄都是女人，男人是靠她们的锤炼才得以成长。《阳光灿烂的日子》的马小军是如此，《邪不压正》的李天然也是如此。\",\"time\":\"2018-07-13\",\"rate\":40}]";
//        String data = "[{\"doubanId\":1422635404,\"name\":\"与碟私奔\",\"avatar\":\"https://img3.doubanio.com/icon/u1305791-1.jpg\",\"votes\":11743,\"text\":\"荒诞中揭示人性，导演处女作应该说太用心了，简直就是想费力而不只是想讨好(巧)观众；场景和特效都非常认真，荒岛，破船的造型……电影就是高度文明的人再退回到猴子原始时代的现代寓言，这也足见一个酝酿多年的演而优则导的处女作的良苦『野心』，如果多一些这样的的导演，那么我们的国产電影好看了\",\"time\":\"2018-08-09\",\"rate\":40},{\"doubanId\":1430426767,\"name\":\"掉线\",\"avatar\":\"https://img1.doubanio.com/icon/u45134592-68.jpg\",\"votes\":8548,\"text\":\"没想到黄渤居然拍了一出魔幻现实主义，故事相当好，但黄渤并没有被故事支配，十分自如的展现了自己的影像想法，作为处女作非常成熟。虽然片子也有些这样那样的遗憾，但该有的荒诞，该有的黑暗都成功地表达了出来，即便不带“演员跨界执导”的滤镜，这也是一年中排的上号的国产佳片。\",\"time\":\"2018-08-08\",\"rate\":40},{\"doubanId\":1431501185,\"name\":\"随遇而安\",\"avatar\":\"https://img3.doubanio.com/icon/u130591704-1.jpg\",\"votes\":5697,\"text\":\"新导演的通病，想要的太多，逻辑的混乱，节奏的缺失，铺垫的冗长，演员过于夸张的表演，物资充盈的“荒岛”，人性的拷问毫无说服力，因为黄渤，我反而更失望\",\"time\":\"2018-08-10\",\"rate\":20},{\"doubanId\":1315761564,\"name\":\"陆支羽\",\"avatar\":\"https://img3.doubanio.com/icon/u2866549-132.jpg\",\"votes\":6799,\"text\":\"1.与世孤绝的荒岛群像，物竞天择的社会缩影。2.没想到，黄渤真的拍了个“蝇王”式的反乌托邦寓言；无论票房口碑如何，这样的尝试已然足够攒脸。3.那场倒置船戏挺魔幻，有种集体着魔的快感，曾剑的摄影很加分。4.的确，这样一部电影，在华语影坛还不曾出现过同类。\",\"time\":\"2018-08-09\",\"rate\":40},{\"doubanId\":1427723087,\"name\":\"影志\",\"avatar\":\"https://img1.doubanio.com/icon/u1005928-127.jpg\",\"votes\":4826,\"text\":\"多人版《荒岛求生》，孤岛版《欢迎来到东莫村》…TB船车也太高级了，都想作死体验一把了，哈哈哈！故事其实挺通俗易懂、当荒岛生存冒险片看也不赖。可解读内容多，黄渤导演野心很大，也很努力想拍好，虽然想讲的东西略多，片子再短一点或许更好。看《瓣嘴》黄渤自造差评，看完正片后超出了预期。片尾两彩蛋，字幕最后的徐峥彩蛋加分。\",\"time\":\"2018-08-05\",\"rate\":40},{\"doubanId\":1427736602,\"name\":\"无耻不混蛋\",\"avatar\":\"https://img3.doubanio.com/icon/u1400288-41.jpg\",\"votes\":3724,\"text\":\"为黄渤打call一百次，话先撂这了。可供解读的点，可能有100种。最后半小时把我“惊哭了”。【导演黄渤可期。】如此的黑色幽默，如此的荒诞，又如此的残酷，如此的厚重，关于中国社会近50多年来最完整的隐寓。看前半部，你本以为这仅仅只是大型荒岛求生真人秀节目，看到后面，你会啪啪打脸。\",\"time\":\"2018-08-05\",\"rate\":40},{\"doubanId\":1427588743,\"name\":\"二十二岛主\",\"avatar\":\"https://img1.doubanio.com/icon/u48369193-7.jpg\",\"votes\":3862,\"text\":\"实名表扬张艺兴，在一众资深演员中竟然能够成为戏眼，这要感谢黄渤的剧本和信任，给了他一个有发挥的角色。说回电影本身，整个观影过程就像心电图一样起起伏伏，每当觉得挺好的地方，接下来很快就垮掉，可当不足的地方过去了，又会有新的亮点出现。毕竟是处女作，不能因为导演是黄渤就拿超级高的标准来要求他，第一部戏就敢挑战具有如此丰富元素的大群戏，这种勇气只怕姜文都不具备。姜文第5部才拍出了一步之遥，而黄渤第一部就拍出来了，结尾从悬崖上坠落的那场戏和马走日跳下来的那一幕太像，并留给了观众足够多的解读空间。所以已经可以预见到影片正式上映后可怕的两极分化，但我可以肯定的说，这是2018华语电影最具勇气的尝试之一。最后想说，下一部戏也别什么广告都植入，在末世寓言面前，开心消消乐和快手真的太low了。\",\"time\":\"2018-08-05\",\"rate\":40},{\"doubanId\":1431505986,\"name\":\"紫苑\",\"avatar\":\"https://img3.doubanio.com/icon/u120779192-5.jpg\",\"votes\":1970,\"text\":\"这么高分眼睛瞎了么，一点内涵都没有的套路式电影，王迅王宝强黄渤全程闹腾装疯卖丑，看的脑仁疼。就张艺兴人设好一些还算正常，舒淇也全程台词出戏\",\"time\":\"2018-08-10\",\"rate\":10},{\"doubanId\":1430639843,\"name\":\"拐了\",\"avatar\":\"https://img3.doubanio.com/icon/u1171561-92.jpg\",\"votes\":1557,\"text\":\"就很费劲大张旗鼓地拍了一个新概念作文，又硬塞进来许多商业诉求，真不如直接做个话剧……\",\"time\":\"2018-08-09\",\"rate\":20},{\"doubanId\":1429838363,\"name\":\"rrr\",\"avatar\":\"https://img3.doubanio.com/icon/u1335633-144.jpg\",\"votes\":2419,\"text\":\"预告片真的误事儿，是我看过的第一部预告片拖正片后腿的电影。本来看完预告片之后有点担心，但黄渤作为新人导演没有让我失望，结束后的鼓掌发自内心。可以说是我近年来看过的最好的华语电影，超过了药神，细节控会格外开心。晚点来补长评。\",\"time\":\"2018-08-08\",\"rate\":50},{\"doubanId\":1430419992,\"name\":\"谢谢你们的鱼\",\"avatar\":\"https://img1.doubanio.com/icon/u1752908-27.jpg\",\"votes\":2265,\"text\":\"黄渤这一次真的拼了，用一群人荒岛求生的故事外壳好好地搞了一出人性实验，黑色，荒诞，疯狂，从生存的法则，部落的建立，文明的诞生，货币的诞生讲到政权的形成以及乌托邦社会的破灭，而在其中穿插着一个男人犹如摩西一般的成长历程，整个剧本的野心极大，不论是宏观上还是微观上都言之有物，连张艺兴都会演戏了！\",\"time\":\"2018-08-08\",\"rate\":40},{\"doubanId\":1431130042,\"name\":\"乌鸦火堂\",\"avatar\":\"https://img3.doubanio.com/icon/u2297669-12.jpg\",\"votes\":1989,\"text\":\"简明人类发展史，乌托邦寓言，反向《蝇王》。黄渤导演处女作野心很大，荒诞讽刺的手法反映现实意义，三个阶段代表了生存→物质→精神的进阶，内核表达：文明是人类兽性的遮羞布。而最终的和谐告诉我们，真相才是统治者们最害怕的东西。电影更希望人能直面人性之恶，这样才能认识自身的不完美。新导演总是想表达很多内容，还有政治经济学在里面，导致片子有些过满篇幅过长，但这样的片子在华语片中是很少见的，实验色彩很重的商业片\",\"time\":\"2018-08-09\",\"rate\":40},{\"doubanId\":1431417214,\"name\":\"嗨森Dave\",\"avatar\":\"https://img3.doubanio.com/icon/u74505533-2.jpg\",\"votes\":1198,\"text\":\"好演员未必能做好导演。\",\"time\":\"2018-08-10\",\"rate\":10},{\"doubanId\":1404127319,\"name\":\"超新星芋圆兔\",\"avatar\":\"https://img3.doubanio.com/icon/u3004462-11.jpg\",\"votes\":2106,\"text\":\"ume的见面会。黄渤本人比电影里好看多了。电影本身不错，情节设置很好，就是开头的地方拍的有些粗糙，到岛上之后就开始精彩了。张艺兴的表演有突破。看宣传片以为电影要凉，结果居然意外的好看。推荐~\",\"time\":\"2018-08-03\",\"rate\":40},{\"doubanId\":1431480473,\"name\":\"项大妮\",\"avatar\":\"https://img3.doubanio.com/icon/u157121717-2.jpg\",\"votes\":986,\"text\":\"我是看了网上的影评才去看的，上当了，真的很一般，说得直白一点：有点像裹脚布，又臭又长！ 不信你们可以去看看。\",\"time\":\"2018-08-10\",\"rate\":10},{\"doubanId\":1429527468,\"name\":\"居无间\",\"avatar\":\"https://img1.doubanio.com/icon/u49549492-7.jpg\",\"votes\":1579,\"text\":\"如果对位近来的喜剧，想到的是《生存家族》，极端情境下，人际关系重新洗牌。它在生存的基础上，做的是关于权力的社会寓言，顺便安插了《杀生》的议题。最直接的象征物是那个倒置的轮船，180度的旋转镜头和蜥蜴主观镜头的想法不错。影片若是能再暗黑些，会更好。张艺兴撑得起场子，黄渤押宝押对了。这也是近年来用王宝强用得最好的一次。\",\"time\":\"2018-08-07\",\"rate\":40},{\"doubanId\":1431096280,\"name\":\"暄\",\"avatar\":\"https://img1.doubanio.com/icon/u124497683-7.jpg\",\"votes\":771,\"text\":\"澳洲提前上映看完觉得比期待要好 zyx演技真的不怎么样 和舒淇的感情线有点不必要 中途穿插的一些画面超级尬 但可以看出黄渤对于电影还是有一些思考的\",\"time\":\"2018-08-09\",\"rate\":20},{\"doubanId\":1431424070,\"name\":\"库克船长\",\"avatar\":\"https://img3.doubanio.com/icon/u162940856-1.jpg\",\"votes\":834,\"text\":\"中国不适合拍这种片。\",\"time\":\"2018-08-10\",\"rate\":10},{\"doubanId\":1427689810,\"name\":\"turbine\",\"avatar\":\"https://img3.doubanio.com/icon/u9611861-3.jpg\",\"votes\":1722,\"text\":\"人类社会简明发展史，容量有限群戏捉襟见肘，话剧可能是这个荒诞戏码更好的载体，不过你们的张艺兴确实上了一个台阶。\",\"time\":\"2018-08-05\",\"rate\":40},{\"doubanId\":1431408282,\"name\":\"DereK\",\"avatar\":\"https://img3.doubanio.com/icon/u1168182-10.jpg\",\"votes\":660,\"text\":\"上个月看了猫眼保密试映。全片可以看出黄渤剖析人性和社会乱象的野心。部分情节片不乏致敬「2001:太空漫游」、人类繁衍的探讨来衬托其命题宏大。但全片问题却非常突出，毫无铺垫的人物弧光转换，散乱的分支剧情对主题和节奏的削弱是致命的，杂糅的主题让电影看起来更像大杂烩。预计票房大扑街。\",\"time\":\"2018-08-10\",\"rate\":20}]";

        String id = getArguments().getString("id");
        String url = "http://192.168.115.70:3000/movie/comment/" + id;
//        String url = "http://10.0.2.2:3000/movie/comment/" + id;
        HttpUtil.sendHttpRequest(url, new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String getData = response.body().string();
                parseJson(getData);

                if(response.isSuccessful()){
                    Message msg = new Message();
                    msg.what = ResponseSuccessful;
                    handler.sendMessage(msg);
                }
            }
            @Override
            public void onFailure(Call call, IOException e) {
            }

        });

        return view;
    }

    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            recyclerView = (RecyclerView) view.findViewById(R.id.comment_list);
            manager = new LinearLayoutManager(getActivity());
            adapter = new CommentAdapter(user_Name, user_Image, comment, comment_Date, comment_votes);

            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);
        }
    };


    private void parseJson(String data){
        Gson gson = new Gson();
        List<Movie.Comment> comment_l = gson.fromJson(data, new TypeToken<List<Movie.Comment>>(){}.getType());

        for(Movie.Comment c : comment_l){
            user_Name.add(c.getName());
            user_Image.add(c.getAvatar());
            comment.add(c.getText());
            comment_Date.add(c.getTime());
            comment_votes.add(c.getVotes());
        }
    }

    /**
     * list的适配器定义
     */
    public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

        private List<String> user_Name;
//        private List<Float> user_Rating;
        private List<String> user_ImageURL;
        private List<String> comment_votes;
        private List<String> comment;
        private List<String> comment_Date;

        private CircleImageView image;

        class ViewHolder extends RecyclerView.ViewHolder{

            TextView name_text;
//            TextView rating_text;
            TextView commentVotes_text;
            TextView comment_text;
            TextView commentDate_text;

            CircleImageView user_Image;

            public ViewHolder(View view){
                super(view);
                name_text = (TextView) view.findViewById(R.id.user_name);
//            rating_text = (TextView) view.findViewById()
                commentVotes_text = (TextView) view.findViewById(R.id.goodnum);
                comment_text = (TextView) view.findViewById(R.id.comment);
                commentDate_text = (TextView) view.findViewById(R.id.comment_date);

                user_Image = (CircleImageView) view.findViewById(R.id.user_Image);
            }

        }

        public CommentAdapter(List<String> user_Name, List<String> user_ImageURL, List<String> comment, List<String> comment_Date, List<String> comment_votes){
            this.user_Name = user_Name;
//            this.user_Rating = user_Rating;
            this.user_ImageURL = user_ImageURL;
            this.comment_votes = comment_votes;
            this.comment = comment;
            this.comment_Date = comment_Date;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
            return new ViewHolder(view);
        }

        class A{
            Bitmap bmp;
            ImageView image;
        }
        private void setImage(final String url, final ImageView i){

            new Thread(new Runnable(){
                @Override
                public void run(){
                    Bitmap bmp = HttpGetImages.getURLImage(url);
                    Message msg = new Message();

                    A a = new A();
                    a.bmp = bmp;
                    a.image = i;

                    msg.what = 1;
                    msg.obj = a;
                    handler.sendMessage(msg);
                }
            }).start();
        }

        public Handler handler = new Handler() {
            @Override
            public void handleMessage(final Message msg){
                switch (msg.what){
                    case 1:
                        A a = (A) msg.obj;
                        ImageView imv = a.image;
                        Bitmap bmp = a.bmp;
                        imv.setImageBitmap(bmp);

                        break;
                    default:
                        break;
                }
            }
        };


        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.name_text.setText(user_Name.get(position));
            holder.commentVotes_text.setText(comment_votes.get(position));
            holder.comment_text.setText(comment.get(position));
            holder.commentDate_text.setText(comment_Date.get(position));

            setImage(user_ImageURL.get(position), holder.user_Image);
        }


        /**
         * 只加载出四个
         */
        @Override
        public int getItemCount() {
            if(user_Name.size() >= 4)
                return 4;
            else
                return user_Name.size();
        }
    }
}
