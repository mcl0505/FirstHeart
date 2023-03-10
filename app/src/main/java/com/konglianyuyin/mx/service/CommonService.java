package com.konglianyuyin.mx.service;

import com.konglianyuyin.mx.bean.ActiveListBean;
import com.konglianyuyin.mx.bean.AdminUser;
import com.konglianyuyin.mx.bean.AgreeCpResult;
import com.konglianyuyin.mx.bean.AgreementBean;
import com.konglianyuyin.mx.bean.AliInfor;
import com.konglianyuyin.mx.bean.AllCommentBean;
import com.konglianyuyin.mx.bean.AllRoomBean;
import com.konglianyuyin.mx.bean.BXShuoMingTextBean;
import com.konglianyuyin.mx.bean.BXUserCostBean;
import com.konglianyuyin.mx.bean.BannerBean;
import com.konglianyuyin.mx.bean.BaoXiangBean;
import com.konglianyuyin.mx.bean.BaseBean;
import com.konglianyuyin.mx.bean.BindBean;
import com.konglianyuyin.mx.bean.BoxExchangeBean;
import com.konglianyuyin.mx.bean.BoxJiangChiBean;
import com.konglianyuyin.mx.bean.BoxOpenRecordBean;
import com.konglianyuyin.mx.bean.CPDetailsBean;
import com.konglianyuyin.mx.bean.CashHis;
import com.konglianyuyin.mx.bean.CategorRoomBean;
import com.konglianyuyin.mx.bean.ChaMoneyBean;
import com.konglianyuyin.mx.bean.CodeBean;
import com.konglianyuyin.mx.bean.CollectionRoomListBean;
import com.konglianyuyin.mx.bean.CommentBean;
import com.konglianyuyin.mx.bean.ConstellationBean;
import com.konglianyuyin.mx.bean.DengJiBean;
import com.konglianyuyin.mx.bean.DynamicDetailsBean;
import com.konglianyuyin.mx.bean.DynamicSearchBean;
import com.konglianyuyin.mx.bean.EmojiBean;
import com.konglianyuyin.mx.bean.EnterRoom;
import com.konglianyuyin.mx.bean.FollowBean;
import com.konglianyuyin.mx.bean.GifBean;
import com.konglianyuyin.mx.bean.GiftListBean;
import com.konglianyuyin.mx.bean.GoodsList;
import com.konglianyuyin.mx.bean.GoodsListBean;
import com.konglianyuyin.mx.bean.GuanFangLianXiBean;
import com.konglianyuyin.mx.bean.IncomeBean;
import com.konglianyuyin.mx.bean.JinSheng;
import com.konglianyuyin.mx.bean.Login;
import com.konglianyuyin.mx.bean.LookCommentBean;
import com.konglianyuyin.mx.bean.LotteryBean;
import com.konglianyuyin.mx.bean.LotteryHistoryBean;
import com.konglianyuyin.mx.bean.MeYiDuiBean;
import com.konglianyuyin.mx.bean.MessageYoBean;
import com.konglianyuyin.mx.bean.Microphone;
import com.konglianyuyin.mx.bean.MiniOfficBean;
import com.konglianyuyin.mx.bean.MoneyBean;
import com.konglianyuyin.mx.bean.MusicYinxiao;
import com.konglianyuyin.mx.bean.MyPackBean;
import com.konglianyuyin.mx.bean.MyPersonalCebterBean;
import com.konglianyuyin.mx.bean.OffiMessageBean;
import com.konglianyuyin.mx.bean.OpenBoxBean;
import com.konglianyuyin.mx.bean.OtherBean;
import com.konglianyuyin.mx.bean.OtherUser;
import com.konglianyuyin.mx.bean.PayBean;
import com.konglianyuyin.mx.bean.Rank;
import com.konglianyuyin.mx.bean.RecommenRoomBean;
import com.konglianyuyin.mx.bean.RecommendLabelBean;
import com.konglianyuyin.mx.bean.RecommendedDynamicBean;
import com.konglianyuyin.mx.bean.Register;
import com.konglianyuyin.mx.bean.ReportBean;
import com.konglianyuyin.mx.bean.RequestUpMicrophoneBean;
import com.konglianyuyin.mx.bean.RoomBg;
import com.konglianyuyin.mx.bean.RoomRankBean;
import com.konglianyuyin.mx.bean.RoomType;
import com.konglianyuyin.mx.bean.RoomUsersBean;
import com.konglianyuyin.mx.bean.Search;
import com.konglianyuyin.mx.bean.SearchAdmin;
import com.konglianyuyin.mx.bean.SearchHis;
import com.konglianyuyin.mx.bean.SearchLabelBean;
import com.konglianyuyin.mx.bean.SendAllGemResult;
import com.konglianyuyin.mx.bean.SendGemResult;
import com.konglianyuyin.mx.bean.ShareBean;
import com.konglianyuyin.mx.bean.StartLoftBean;
import com.konglianyuyin.mx.bean.TopicBean;
import com.konglianyuyin.mx.bean.TopicDynamicBean;
import com.konglianyuyin.mx.bean.UnreadBean;
import com.konglianyuyin.mx.bean.UpVideoResult;
import com.konglianyuyin.mx.bean.UpdateApkBean;
import com.konglianyuyin.mx.bean.UserBean;
import com.konglianyuyin.mx.bean.UserFriend;
import com.konglianyuyin.mx.bean.VipBean;
import com.konglianyuyin.mx.bean.VipCenterBean;
import com.konglianyuyin.mx.bean.WaitList;
import com.konglianyuyin.mx.bean.Wxmodel;
import com.konglianyuyin.mx.bean.XuYaoMiZuanBean;
import com.konglianyuyin.mx.bean.Yinxiao;
import com.konglianyuyin.mx.bean.ZengSongBean;
import com.konglianyuyin.mx.bean.ZengSongHisBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import static com.konglianyuyin.mx.app.Api.APP_DOMAIN;
import static com.konglianyuyin.mx.app.Api.APP_DOMAIN_NoApi;


/**
 * ??????
 */
public interface CommonService {

    /**
     * ??????
     */
    @POST("registered")
    @FormUrlEncoded
    Observable<Login> register(@Field("phone") String phone,
                                  @Field("sex") String sex,
                                  @Field("birthday") String birthday,
                                  @Field("nickname") String nickname,
                                  @Field("headimgurl") String headimgurl,
                                  @Field("system") String system,
                                  @Field("channel") String channel,
                                  @Field("code") String code,
                                  @Field("pusername") String pusername);

    /**
     * ??????
     */
    @POST(APP_DOMAIN + "phoneLogin")
    @FormUrlEncoded
    Observable<Login> login(@Field("phone") String phone,
                            @Field("code") String code);

    /**
     * ??????????????????
     */
    @POST(APP_DOMAIN + "login")
    @FormUrlEncoded
    Observable<Login> loginPass(@Field("phone") String phone,
                                @Field("pass") String pass);

    /**
     * ?????????
     */
    @POST(APP_DOMAIN + "verification")
    @FormUrlEncoded
    Observable<CodeBean> verification(
            @Field("phone") String phone,
            @Field("type") String type
    );

    /**
     * ???????????????
     */
    @POST(APP_DOMAIN + "is_verification")
    @FormUrlEncoded
    Observable<Register> is_verification(@Field("phone") String phone, @Field("code") String rand);

    /**
     * ??????????????????????????????
     */
    @POST(APP_DOMAIN + "room_recommend_room")
    @FormUrlEncoded
    Observable<RecommenRoomBean> getrecommendroom(@Field("categories") int categories,
                                                  @Field("page") int page);

    /**
     * ???????????????????????????
     */
    @POST(APP_DOMAIN + "room_recommend_categories")
    Observable<CategorRoomBean> room_categories();

    /**
     * ??????????????????
     */
    @POST(APP_DOMAIN + "is_popular")
    Observable<RecommenRoomBean> is_popular();

    /**
     * ????????????????????????
     */
    @POST(APP_DOMAIN + "secret_chat")
    Observable<RecommenRoomBean> secret_chat();

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "zengsong")
    @FormUrlEncoded
    Observable<ZengSongBean> zengsong(@Field("goto_uid") String goto_uid,
                                      @Field("money") String money,
                                      @Field("token") String token
                                      );

    /**
     * ??????????????????
     */
    @GET(APP_DOMAIN + "zhengsonglog")
    Observable<ZengSongHisBean> zhengsonglog(@Query("page") String page);

    /**
     * ?????????
     */
    @POST(APP_DOMAIN + "star_loft")
    @FormUrlEncoded
    Observable<StartLoftBean> star_loft(@Field("sex") int sex);

    /**
     * ?????????
     */
    @POST(APP_DOMAIN + "carousel")
    @FormUrlEncoded
    Observable<BannerBean> carousel(@Field("xx") String xx);

    /**
     * ??????????????????
     */
    @POST(APP_DOMAIN + "edit_room")
    @FormUrlEncoded
    Observable<Register> create_or_edit_room(
            @Field("room_pass") String room_pass,
            @Field("room_name") String room_name,
            @Field("room_type") String room_type,
            @Field("room_intro") String room_intro,

            @Field("room_background") String room_background,
            @Field("uid") String uid,
            @Field("cover") String room_cover
    );

    /**
     * ??????????????????
     */
    @POST(APP_DOMAIN + "room_type")
    @FormUrlEncoded
    Observable<RoomType> room_type(@Field("xx") String xx);

    /**
     * ?????????????????????*
     */
    @POST(APP_DOMAIN + "room_background")
    @FormUrlEncoded
    Observable<RoomBg> room_background(@Field("xx") String xx);

    /**
     * ??????????????????*
     */
    @POST(APP_DOMAIN + "gift_list")
    @FormUrlEncoded
    Observable<GiftListBean> gift_list(@Field("user_id") String user_id);

    /**
     * ????????????*
     */
    @POST(APP_DOMAIN + "enter_room")
    @FormUrlEncoded
    Observable<EnterRoom> enter_room(
            @Field("uid") String uid,
            @Field("room_pass") String room_pass,
            @Field("user_id") String user_id
    );

    /**
     * ??????????????????
     */
    @POST(APP_DOMAIN + "microphone_status")
    @FormUrlEncoded
    Observable<Microphone> microphone_status(
            @Field("uid") String uid);

    /**
     * ??????
     */
    @POST(APP_DOMAIN + "up_microphone")
    @FormUrlEncoded
    Observable<UpVideoResult> up_microphone(
            @Field("uid") String uid, @Field("user_id") String user_id, @Field("position") String position);

    /**
     * ??????
     */
    @POST(APP_DOMAIN + "go_microphone")
    @FormUrlEncoded
    Observable<BaseBean> go_microphone(
            @Field("uid") String uid, @Field("user_id") String user_id);

    /**
     * ????????????*
     */
    @POST(APP_DOMAIN + "upload_music")
    @FormUrlEncoded
    Observable<BaseBean> upload_music(
            @Field("singer") String singer,
            @Field("music") String music,
            @Field("music_name") String music_name,
            @Field("upload_name") String upload_name);

    /**
     * ????????????*
     */
    @POST(APP_DOMAIN + "dynamic_details")
    @FormUrlEncoded
    Observable<DynamicDetailsBean> dynamic_details(
            @Field("user_id") String user_id,
            @Field("sort") String sort,
            @Field("page") String page,
            @Field("id") String id);

    /**
     * ????????????*
     */
    @POST(APP_DOMAIN + "send_dynamic")
    @Multipart
    Observable<BaseBean> send_dynamic(
            @Part("user_id") int user_id,
            @QueryMap Map<String, Object> op,
            @Part MultipartBody.Part audioFile,
            @Part MultipartBody.Part videoFile,
            @Part MultipartBody.Part img1File,
            @Part MultipartBody.Part img2File,
            @Part MultipartBody.Part img3File,
            @Part MultipartBody.Part img4File,
            @Part MultipartBody.Part img5File,
            @Part MultipartBody.Part img6File);

    /**
     * ?????????*
     */
    @POST(APP_DOMAIN + "dynamic_comment")
    @FormUrlEncoded
    Observable<BaseBean> dynamic_comment(
            @Field("b_dynamic_id") int b_dynamic_id,
            @Field("user_id") int user_id,
            @Field("content") String content);

    /**
     * ????????????*
     */
    @POST(APP_DOMAIN + "dynamic_collection")
    @FormUrlEncoded
    Observable<BaseBean> dynamic_collection(
            @Field("b_dynamic_id") int b_dynamic_id,
            @Field("user_id") int user_id);

    /**
     * ??????????????????*
     */
    @POST(APP_DOMAIN + "dynamic_search")
    @FormUrlEncoded
    Observable<DynamicSearchBean> dynamic_search(
            @Field("search") int search);

    /**
     * ??????????????????*
     */
    @POST(APP_DOMAIN + "dynamicTjList")
    @FormUrlEncoded
    Observable<RecommendedDynamicBean> recommended_dynamic(
            @Field("user_id") String userId,
            @Field("page") String page);

    /**
     * ??????????????????*
     */
    @POST(APP_DOMAIN + "dynamicNewList")
    @FormUrlEncoded
    Observable<RecommendedDynamicBean> new_dynamic(
            @Field("user_id") String user_id,
            @Field("page") String page);

    /**
     * ??????????????????*
     */
    @POST(APP_DOMAIN + "topic")
    @FormUrlEncoded
    Observable<TopicBean> topic(@Field("type") String type);

    /**
     * ?????????????????????*
     */
    @POST(APP_DOMAIN + "topic_dynamic")
    @FormUrlEncoded
    Observable<TopicDynamicBean> topic_dynamic(
            @Field("tags") String tags,
            @Field("user_id") String user_id,
            @Field("page") String page,
            @Field("type") String type);

    /**
     * ?????????*
     */
    @POST(APP_DOMAIN + "dynamics_hand")
    @FormUrlEncoded
    Observable<BaseBean> dynamic_praise(
            @Field("user_id") String user_id,
            @Field("target_id") String target_id,
            @Field("type") String type,
            @Field("hand") String hand);


    /**
     * ?????????*
     */
    @POST(APP_DOMAIN + "comment_praise")
    @FormUrlEncoded
    Observable<BaseBean> comment_praise(
            @Field("b_dynamic_id") int b_dynamic_id,
            @Field("user_id") int user_id);


    /**
     * ???????????????????????????
     */
    @POST(APP_DOMAIN + "is_top")
    @FormUrlEncoded
    Observable<RecommenRoomBean> is_top(@Field("xs") String xx);

    /**
     * ???????????????????????????
     */
    @POST(APP_DOMAIN + "is_pass")
    @FormUrlEncoded
    Observable<BaseBean> is_pass(@Field("uid") String uid);

    /**
     * ??????
     */
    @POST(APP_DOMAIN + "shut_microphone")
    @FormUrlEncoded
    Observable<BaseBean> shut_microphone(@Field("uid") String uid, @Field("position") int position);

    /**
     * ?????????
     */
    @POST(APP_DOMAIN + "open_microphone")
    @FormUrlEncoded
    Observable<BaseBean> open_microphone(@Field("uid") String uid,
                                         @Field("position") int position);

    /**
     * ????????????????????????
     */
    @POST(APP_DOMAIN + "get_other_user")
    @FormUrlEncoded
    Observable<OtherUser> get_other_user(
            @Field("uid") String uid,
            @Field("user_id") String user_id,
            @Field("my_id") String my_id);

    /**
     * ??????????????? ????????????
     */
    @POST(APP_DOMAIN + "shut_sound")
    @FormUrlEncoded
    Observable<BaseBean> shut_sound(@Field("uid") String uid, @Field("position") int position);

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "is_sound")
    @FormUrlEncoded
    Observable<JinSheng> is_sound(@Field("uid") String uid, @Field("user_id") String is_sound);

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "remove_sound")
    @FormUrlEncoded
    Observable<JinSheng> remove_sound(@Field("uid") String uid, @Field("user_id") String sound_id);

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "is_black")
    @FormUrlEncoded
    Observable<JinSheng> is_black(@Field("uid") String uid, @Field("user_id") String black_id);

    /**
     * ??????
     */
    @POST(APP_DOMAIN + "room_mykeep")
    @FormUrlEncoded
    Observable<BaseBean> room_mykeep(@Field("uid") String uid, @Field("user_id") String user_id);


    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "remove_mykeep")
    @FormUrlEncoded
    Observable<BaseBean> remove_mykeep(@Field("uid") String uid, @Field("user_id") String user_id);

    /**
     * ?????????
     */
    @POST(APP_DOMAIN + "ranking")
    @FormUrlEncoded
    Observable<Rank> leaderboard(
            @Field("class") String type,
            @Field("type") String date,
            @Field("user_id") String user_id);

    /**
     * ???????????????
     */
    @POST(APP_DOMAIN + "is_admin")
    @FormUrlEncoded
    Observable<BaseBean> is_admin(
            @Field("uid") String uid,
            @Field("admin_id") String admin_id);

    /**
     * ???????????????
     */
    @POST(APP_DOMAIN + "remove_admin")
    @FormUrlEncoded
    Observable<BaseBean> remove_admin(
            @Field("uid") String uid,
            @Field("admin_id") String admin_id);

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "quit_room")
    @FormUrlEncoded
    Observable<BaseBean> quit_room(
            @Field("uid") String uid,
            @Field("user_id") String user_id);


    /**
     * ??????????????????
     */
    @POST(APP_DOMAIN + "temproom")
    @FormUrlEncoded
    Observable<BaseBean> quit_room666666666(
            @Field("id") String id,
            @Field("room") String room);


    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "follow")
    @FormUrlEncoded
    Observable<BaseBean> follow(
            @Field("user_id") String uid,
            @Field("followed_user_id") String followed_user_id);

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "cancel_follow")
    @FormUrlEncoded
    Observable<BaseBean> cancel_follow(
            @Field("user_id") String uid,
            @Field("followed_user_id") String followed_user_id);

    /**
     * ???????????????
     */
    @POST(APP_DOMAIN + "getRoomUsers")
    @FormUrlEncoded
    Observable<AdminUser> getRoomUsers(
            @Field("uid") String uid);

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "search_user")
    @FormUrlEncoded
    Observable<SearchAdmin> search_user(
            @Field("uid") String uid,
            @Field("keywords") String keywords);


    /**
     * ????????????is_black
     */
    @POST(APP_DOMAIN + "friend_list")
    @FormUrlEncoded
    Observable<BaseBean> friend_list(
            @Field("user_id") String user_id);

    /**
     * ??????????????????
     */
    @POST(APP_DOMAIN + "not_speak_status")
    @FormUrlEncoded
    Observable<BaseBean> not_speak_status(
            @Field("uid") String uid,
            @Field("user_id") String user_id);

    /**
     * ??????
     */
    @POST(APP_DOMAIN + "now_music")
    @FormUrlEncoded
    Observable<MusicYinxiao> get_sound(@Field("id") String id, @Field("user_id") String user_id);


    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "local_musics")
    @FormUrlEncoded
    Observable<Yinxiao> get_music(
            @Field("keywords") String keywords,
            @Field("page") String page,
            @Field("user_id") String user_id
    );

    /**
     * ??????
     */
    @POST(APP_DOMAIN + "emoji_list")
    @FormUrlEncoded
    Observable<EmojiBean> emoji_list(
            @Field("xx") String xx);

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "get_emoji")
    @FormUrlEncoded
    Observable<GifBean> get_emoji(
            @Field("id") String id);

    /**
     * ??????????????????
     */

    @POST(APP_DOMAIN + "dynamicFollowList")
    @FormUrlEncoded
    Observable<RecommendedDynamicBean> get_GZ_dynamic(@Field("user_id") String user_id,
                                                      @Field("page") String page);

    /**
     * ?????????,??????,??????,????????????????????????
     * type 1??????  2??????   3??????  4??????   5?????? 6????????????
     */
    @POST(APP_DOMAIN + "merge_dynamic")
    @FormUrlEncoded
    Observable<MeYiDuiBean> getMeYiDui(@Field("user_id") String user_id,
                                       @Field("my_id") String my_id,
                                       @Field("type") String type,
                                       @Field("page") String page);

    /**
     * ??????????????????
     * user_id ??????id
     */
    @POST(APP_DOMAIN + "unreadMessage")
    @FormUrlEncoded
    Observable<UnreadBean> getUnreadMessage(@Field("user_id") String user_id);

    /**
     * ????????????
     * user_id ??????ID
     */
    @POST(APP_DOMAIN + "message")
    @FormUrlEncoded
    Observable<MessageYoBean> getMessageYo(@Field("user_id") String user_id);

    /**
     * ????????????
     * user_id ??????ID
     * hf_uid ?????????ID
     * id ??????ID
     */
    @POST(APP_DOMAIN + "lookComments")
    @FormUrlEncoded
    Observable<LookCommentBean> getLookComment(@Field("user_id") String user_id,
                                               @Field("hf_uid") String hf_uid,
                                               @Field("id") String id);

    /**
     * ????????????????????????????????????
     * id ??????Id
     * user_id ??????ID
     */
    @POST(APP_DOMAIN + "allComment")
    @FormUrlEncoded
    Observable<AllCommentBean> getAllComment(@Field("id") String id,
                                             @Field("user_id") String user_id,
                                             @Field("page") String page);

    /**
     * ????????????????????????
     * id ?????????ID
     * pid ????????????????????????????????????????????????  ??????????????????0
     * user_id ?????????ID
     * content ???????????????
     */
    @POST(APP_DOMAIN + "dynamic_comment")
    @FormUrlEncoded
    Observable<CommentBean> setComment(@Field("id") String id,
                                       @Field("pid") String pid,
                                       @Field("user_id") String user_id,
                                       @Field("content") String content);

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "gift_queue")
    @FormUrlEncoded
    Observable<SendGemResult> gift_queue(
            @Field("id") String id,
            @Field("uid") String uid,
            @Field("user_id") String user_id,
            @Field("fromUid") String fromUid,
            @Field("type") String type,
            @Field("num") String num,
            @Field("token") String token

    );

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "gift_queue")
    @FormUrlEncoded
    Observable<SendGemResult> gift_queue_all(
            @Field("id") String id,
            @Field("uid") String uid,
            @Field("user_id") String user_id,
            @Field("fromUid") String fromUid,
            @Field("type") String type,
            @Field("num") String num,
            @Field("token") String token

    );

    /**
     * ??????
     */
    @POST(APP_DOMAIN + "addWaid")
    @FormUrlEncoded
    Observable<WaitList> addWaid(
            @Field("uid") String uid,
            @Field("user_id") String user_id
    );

    /**
     * ??????
     */
    @GET(APP_DOMAIN + "temproom")
    Observable<AllRoomBean> getAllRoom();

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "waitList")
    @FormUrlEncoded
    Observable<WaitList> waitList(
            @Field("uid") String uid,
            @Field("user_id") String user_id
    );


    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "delWait")
    @FormUrlEncoded
    Observable<BaseBean> delWait(
            @Field("user_id") String user_id
    );

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "androidGoodsList")
    @FormUrlEncoded
    Observable<GoodsList> goodsList(
            @Field("user_id") String user_id
    );

    /**
     * ??????,??????,??????
     */
    @POST(APP_DOMAIN + "userFriend")
    @FormUrlEncoded
    Observable<UserFriend> userFriend(
            @Field("user_id") String user_id,
            @Field("type") String type,
            @Field("page") String page
    );

    /**
     * ??????????????????
     */
    @POST(APP_DOMAIN + "get_user_info")
    @FormUrlEncoded
    Observable<UserBean> get_user_info(
            @Field("user_id") String user_id
    );

    /**
     * ??????????????????
     */
    @POST(APP_DOMAIN + "rechargePay")//1?????????2??????
    @FormUrlEncoded
    Observable<PayBean> rechargePay(
            @Field("user_id") String user_id,
            @Field("goods_id") String goods_id,
            @Field("type") String type
    );

    /**
     * ??????
     */
    @POST(APP_DOMAIN + "rechargePay")//1?????????2??????
    @FormUrlEncoded
    Observable<Wxmodel> rechargeWxPay(
            @Field("user_id") String user_id,
            @Field("goods_id") String goods_id,
            @Field("type") String type
    );

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "get_talk_labels")
    @FormUrlEncoded
    Observable<RecommendLabelBean> getLabel(@Field("xx") String xx);

    /**
     * ????????????
     * keywords ?????????
     */
    @POST(APP_DOMAIN + "search_labels")
    @FormUrlEncoded
    Observable<SearchLabelBean> getSouSuoLabel(@Field("keywords") String keywords);

    /**
     * ???????????????
     */
    @POST(APP_DOMAIN + "pull_black")
    @FormUrlEncoded
    Observable<BaseBean> pull_black(@Field("user_id") String user_id,
                                    @Field("from_uid") String from_uid);

    /**
     * ???????????????
     */
    @POST(APP_DOMAIN + "cancel_black")
    @FormUrlEncoded
    Observable<BaseBean> cancel_black(@Field("user_id") String user_id,
                                      @Field("from_uid") String from_uid);

    /**
     * ???????????????
     */
    @POST(APP_DOMAIN + "blackList")
    @FormUrlEncoded
    Observable<UserFriend> blackList(@Field("user_id") String user_id,
                                     @Field("page") String page);

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "report")
    @FormUrlEncoded
    Observable<ReportBean> report(@Field("xx") String xx);

    /**
     * ??????
     */
    @POST(APP_DOMAIN + "send_report")
    @FormUrlEncoded
    Observable<BaseBean> send_report(
            @Field("user_id") String user_id,
            @Field("img") String img,
            @Field("type") String type,
            @Field("target") String target,
            @Field("report_type") String report_type
    );

    /**
     * ????????????????????????
     */
    @POST(APP_DOMAIN + "official")
    @FormUrlEncoded
    Observable<GuanFangLianXiBean> guanfang(@Field("xx") String xx);

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "official_message")
    @FormUrlEncoded
    Observable<OffiMessageBean> official_message(
            @Field("user_id") String user_id,
            @Field("page") String page
    );

    /**
     * ??????????????????
     */
    @POST(APP_DOMAIN + "clear_message")
    @FormUrlEncoded
    Observable<BaseBean> clear_message(
            @Field("user_id") String user_id
    );

    /**
     * mini??????
     */
    @POST(APP_DOMAIN + "mini_official")
    @FormUrlEncoded
    Observable<MiniOfficBean> mini_official(
            @Field("user_id") String user_id
    );

    /**
     * <<<<<<< HEAD
     * ????????????
     */
    @POST(APP_DOMAIN + "my_store")
    @FormUrlEncoded
    Observable<MoneyBean> my_store(
            @Field("user_id") String user_id
    );

    /**
     * ???????????????
     */
    @POST(APP_DOMAIN + "cleargiftpricecount")
    @FormUrlEncoded
    Observable<BaseBean> cleargiftpricecount(
            @Field("fromUid") String user_id,
            @Field("uid") String uid
    );

    /**
     * ????????????
     */
    @GET(APP_DOMAIN + "tixian_log")
//    @FormUrlEncoded
    Observable<CashHis> tixian_log(
            @Query("user_id") String user_id,
            @Query("page") String page
    );


    /**
     * ????????????
     */
    @GET(APP_DOMAIN + "exchange_log")
//    @FormUrlEncoded
    Observable<CashHis> exchange_log(
            @Query("user_id") String user_id,
            @Query("page") String page
    );

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "exchange")
    @FormUrlEncoded
    Observable<BaseBean> exchange(
            @Field("user_id") String user_id,
            @Field("money") String money
    );

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "ali_oauth_code")
    @FormUrlEncoded
    Observable<BindBean> ali_oauth_code(
            @Field("xx") String xx
    );

    /**
     * ?????????????????????????????????????????????????????????
     * user_id ?????????ID
     * from_uid ?????????ID(???????????????????????????????????????????????????
     */
    @POST(APP_DOMAIN + "user_home_page")
    @FormUrlEncoded
    Observable<MyPersonalCebterBean> getPersonalCabter(@Field("user_id") String user_id,
                                                       @Field("from_uid") String from_uid);

    /**
     * <<<<<<< HEAD
     * ???????????????
     */
    @POST(APP_DOMAIN + "ali_oauth_token")
    @FormUrlEncoded
    Observable<AliInfor> ali_oauth_token(
            @Field("auth_code") String auth_code,
            @Field("user_id") String user_id
    );

    /**
     * ??????
     */
    @POST(APP_DOMAIN + "tixian")
    @FormUrlEncoded
    Observable<BaseBean> tixian(
            @Field("user_id") String user_id,
            @Field("money") String money
    );

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "sfrz_start")
    @FormUrlEncoded
    Observable<PayBean> sfrz_start(
            @Field("user_id") String user_id,
            @Field("name") String name,
            @Field("idno") String idno
    );

    /**
     * ????????????222
     */
    @POST(APP_DOMAIN + "bindIdentity")
    @FormUrlEncoded
    Observable<BaseBean> bindIdentity(
            @Field("Identity_name") String name,
            @Field("Identity_num") String idno
    );

    /**
     * ???????????????
     */
    @POST(APP_DOMAIN + "upalipay")
    @FormUrlEncoded
    Observable<BaseBean> upalipay(
            @Field("ali_nick_name") String name,
            @Field("ali_user_id") String idno
    );

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "sfrz_query")
    @FormUrlEncoded
    Observable<BaseBean> sfrz_query(
            @Field("user_id") String user_id
    );

    /*
     * ????????????
     * phone ?????????
     * code ?????????
     * pass ??????
     */
    @POST(APP_DOMAIN + "forget_pwd")
    @FormUrlEncoded
    Observable<CommentBean> ForGetPWD(@Field("phone") String phone,
                                      @Field("code") String code,
                                      @Field("pass") String pass);

    /**
     * ????????????
     * birthday ??????
     */
    @POST(APP_DOMAIN + "getConstellation")
    @FormUrlEncoded
    Observable<ConstellationBean> getConstellation(@Field("birthday") String birthday);

    /**
     * ??????????????????
     * img ??????
     * nickname ??????
     * sex ??????
     * birthday ??????
     * constellation ??????
     * city ??????
     */
    @POST(APP_DOMAIN + "edit_user_info")
    @FormUrlEncoded
    Observable<OtherBean> setUserInfo(@Field("id") String id,
                                      @Field("img") String img,
                                      @Field("nickname") String nickname,
                                      @Field("sex") String sex,
                                      @Field("birthday") String birthday,
                                      @Field("constellation") String constellation,
                                      @Field("city") String city);

    /**
     * ??????
     */
    @POST(APP_DOMAIN + "one_page")
    @FormUrlEncoded
    Observable<AgreementBean> getAgreement(@Field("type") String type);

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "vip_center")
    @FormUrlEncoded
    Observable<VipCenterBean> getVipCenter(@Field("user_id") String user_id);

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "level_center")
    @FormUrlEncoded
    Observable<DengJiBean> getLevelCenter(@Field("user_id") String user_id);


    /**
     * ??????
     */
    @POST(APP_DOMAIN + "user_income")
    @FormUrlEncoded
    Observable<IncomeBean> user_income(
            @Field("user_id") String user_id
    );

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "searhList")
    @FormUrlEncoded
    Observable<SearchHis> searhList(
            @Field("user_id") String user_id
    );

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "cleanSarhList")
    @FormUrlEncoded
    Observable<BaseBean> cleanSarhList(
            @Field("user_id") String user_id
    );

    /**
     * ??????
     */
    @POST(APP_DOMAIN + "merge_search")
    @FormUrlEncoded
    Observable<Search> merge_search(
            @Field("user_id") String user_id,
            @Field("keywords") String keywords
    );

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "search_all")
    @FormUrlEncoded
    Observable<UserFriend> search_all(
            @Field("user_id") String user_id,
            @Field("keywords") String keywords,
            @Field("type") String type,
            @Field("page") String page
    );

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "search_all")
    @FormUrlEncoded
    Observable<RecommenRoomBean> search_all_room(
            @Field("user_id") String user_id,
            @Field("keywords") String keywords,
            @Field("type") String type,
            @Field("page") String page
    );

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "search_all")
    @FormUrlEncoded
    Observable<RecommendedDynamicBean> search_all_dyni(
            @Field("user_id") String user_id,
            @Field("keywords") String keywords,
            @Field("type") String type,
            @Field("page") String page
    );

//////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * ????????????????????????
     */
    @POST(APP_DOMAIN + "get_room_users")
    @FormUrlEncoded
    Observable<RoomUsersBean> getRoomUsers(@Field("uid") String user_id,
                                           @Field("user_id") String target_id);

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "send_baoshi")
    @FormUrlEncoded
    Observable<SendGemResult> send_baoshi(@Field("id") String id,
                                          @Field("uid") String uid,
                                          @Field("token") String token,
                                          @Field("fromUid") String fromUid,
                                          @Field("num") String num
    );

    /**
     * ???????????????
     */
    @POST(APP_DOMAIN + "send_byk")
    @FormUrlEncoded
    Observable<SendGemResult> send_byk(
            @Field("uid") String uid,
            @Field("token") String token,
            @Field("fromUid") String fromUid,
            @Field("num") String num);

    /**
     * ???????????????CP???
     */
    @POST(APP_DOMAIN + "handle_cp")
    @FormUrlEncoded
    Observable<AgreeCpResult> handle_cp(
            @Field("token") String token,
            @Field("user_id") String user_id,
            @Field("type") String type);

    /**
     * ??????CP??????????????????????????????????????????
     */
    @POST(APP_DOMAIN + "delete_cp")
    @FormUrlEncoded
    Observable<BaseBean> delete_cp(
            @Field("token") String token
    );

    //////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * ?????????????????????
     */
    @POST(APP_DOMAIN + "get_mykeep")
    @FormUrlEncoded
    Observable<CollectionRoomListBean> getCollectionRoomList(@Field("user_id") String user_id);

    /**
     * ???????????????
     */
    @POST(APP_DOMAIN + "OtherRegister")
    @FormUrlEncoded
    Observable<Login> registerOther(@Field("type") String type,
                                    @Field("openid") String openid,
                                    @Field("phone") String phone,
                                    @Field("sex") String sex,
                                    @Field("birthday") String birthday,
                                    @Field("nickname") String nickname,
                                    @Field("headimgurl") String headimgurl,
                                    @Field("pass") String pass,
                                    @Field("system") String system,
                                    @Field("channel") String channel,
                                    @Field("code") String code);

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "OtherLogin")
    @FormUrlEncoded
    Observable<OtherBean> logOther(@Field("openid") String openid,
                                   @Field("type") String type);

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "del_community")
    @FormUrlEncoded
    Observable<CommentBean> delCommunity(@Field("user_id") String user_id,
                                         @Field("id") String id);

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "feedback")
    @FormUrlEncoded
    Observable<BaseBean> feedBack(@Field("user_id") String user_id,
                                  @Field("content") String content,
                                  @Field("img") String img);

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "activeList")
    @FormUrlEncoded
    Observable<ActiveListBean> activeList(@Field("xx") String xx);

    /**
     * ??????vip??????
     */
    @POST(APP_DOMAIN + "get_user_vip")
    @FormUrlEncoded
    Observable<VipBean> get_user_vip(@Field("uid") String user_id, @Field("token") String token);

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "share_dynamic")
    @FormUrlEncoded
    Observable<CommentBean> fenxiang(@Field("user_id") String user_id,
                                     @Field("target_id") String target_id,
                                     @Field("hand") String hand);

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "out_room")
    @FormUrlEncoded
    Observable<BaseBean> out_room(@Field("uid") String uid,
                                  @Field("user_id") String user_id);

    /**
     * ??????????????????
     */
    @POST(APP_DOMAIN + "UntyingAli")
    @FormUrlEncoded
    Observable<BaseBean> UntyingAli(
            @Field("user_id") String user_id);

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "getRoomInfo")
    @FormUrlEncoded
    Observable<EnterRoom> getRoomInfo(
            @Field("uid") String uid);

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "exchange_check")
    @FormUrlEncoded
    Observable<ChaMoneyBean> getMoney(@Field("money") String money);

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "is_follow")
    @FormUrlEncoded
    Observable<FollowBean> is_follow(@Field("user_id") String user_id, @Field("from_uid") String from_uid);

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "my_pack")
    @FormUrlEncoded
    Observable<MyPackBean> my_pack(@Field("type") String type);

    /**
     * ??????
     */
    @POST(APP_DOMAIN + "dress_up")
    @FormUrlEncoded
    Observable<CommentBean> dress_up(@Field("id") String id,
                                     @Field("type") String type);

    /**
     * CP??????
     * id ???cp???id
     */
    @POST(APP_DOMAIN + "cp_desc")
    @FormUrlEncoded
    Observable<CPDetailsBean> cp_desc(@Field("id") String id);

    /**
     * ??????CP
     * id CP???id
     */
    @POST(APP_DOMAIN + "remove_cp")
    @FormUrlEncoded
    Observable<CommentBean> remove_cp(@Field("id") String id);

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "getBoxInfo")
    @FormUrlEncoded
    Observable<BaoXiangBean> getBoxInfo(@Field("xx") String xx);

    /**
     * ????????????????????????
     */
    @POST(APP_DOMAIN + "getAwardList")
    @FormUrlEncoded
    Observable<OpenBoxBean> getAwardList(@Field("keysNum") int keysNum, @Field("bs") String bs);

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "actionBuyKeys")
    @FormUrlEncoded
    Observable<CommentBean> actionBuyKeys(@Field("keysNum") String keysNum);

    /**
     * ???????????????????????????????????????
     */
    @POST(APP_DOMAIN + "getMizuanNum")
    @FormUrlEncoded
    Observable<XuYaoMiZuanBean> getMizuanNum(@Field("keysNum") String keysNum);

    /**
     * ??????????????????????????????
     */
    @POST(APP_DOMAIN + "getAwardWaresList")
    @FormUrlEncoded
    Observable<BoxExchangeBean> getAwardWaresList(@Field("xx") String xx);

    /**
     * ??????????????????
     */
    @POST(APP_DOMAIN + "actionAwardExchange")
    @FormUrlEncoded
    Observable<CommentBean> actionAwardExchange(@Field("waresId") String waresId);

    /**
     * ??????????????????
     */
    @POST(APP_DOMAIN + "getAwardRecordList")
    @FormUrlEncoded
    Observable<BoxOpenRecordBean> getAwardRecordList(@Field("page") int page);

    /**
     * ??????????????????
     */
    @GET(APP_DOMAIN + "getAwardBoxList")
    Observable<BoxJiangChiBean> getAwardBoxList(@Query("box_type") String box_type);

    /**
     * ??????????????????
     */
    @POST(APP_DOMAIN + "getRewardInfo")
    @FormUrlEncoded
    Observable<BXShuoMingTextBean> getRewardInfo(@Field("xx") String xx);

    /**
     * ??????cp???
     */
    @POST(APP_DOMAIN + "open_cp_card")
    @FormUrlEncoded
    Observable<CommentBean> openCPCard(@Field("xx") String xx);

    /**
     * ???????????????
     */
    @POST(APP_DOMAIN + "exchange_mizuan_card")
    @FormUrlEncoded
    Observable<CommentBean> exchangeMizuanCard(@Field("id") String id);

    /**
     * ??????????????????
     * 1?????????2????????? class
     * 1??????2??????3?????? type
     */
    @POST(APP_DOMAIN + "room_ranking")
    @FormUrlEncoded
    Observable<RoomRankBean> room_ranking(@Field("uid") String uid,
                                          @Field("class") String mclass,
                                          @Field("type") String type);

    /**
     * ????????????????????????
     */
    @POST(APP_DOMAIN + "Newmethod/share")
    @FormUrlEncoded
    Observable<ShareBean> get_qr_code(@Field("newtoken") String newtoken,
                                      @Field("uid") String uid);

    /**
     * ???????????????
     */
    @POST(APP_DOMAIN + "Newmethod/luckdraw")
    @FormUrlEncoded
    Observable<LotteryBean> lottery(@Field("newtoken") String newtoken,
                                    @Field("uid") String uid,
                                    @Field("pokernum") String pokernum,
                                    @Field("price") String price);


    /**
     * ?????????????????????
     */
    @POST(APP_DOMAIN + "Newmethod/winlist")
    @FormUrlEncoded
    Observable<LotteryHistoryBean> lotteryHistory(@Field("newtoken") String newtoken,
                                                  @Field("uid") String uid,
                                                  @Field("page") int page);

    /**
     * ??????????????????
     */
    @POST(APP_DOMAIN + "Newmethod/miclist")
    @FormUrlEncoded
    Observable<RequestUpMicrophoneBean> requestUpMicrophoneList(@Field("newtoken") String newtoken,
                                                                @Field("uid") String uid,
                                                                @Field("roomid") String roomid);

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "Newmethod/upmic")
    @FormUrlEncoded
    Observable<BaseBean> requestUpMicrophone(@Field("newtoken") String newtoken,
                                                @Field("uid") String uid,
                                                @Field("roomid") String roomid);

    /**
     * ?????????????????????
     */
    @POST(APP_DOMAIN + "cleargiftpricecount")
    @FormUrlEncoded
    Observable<BaseBean> clearAllMlZ(@Field("newtoken") String newtoken, @Field("uid") String uid, @Field("roomid") String roomid);

    /**
     * ?????????????????????
     */
    @POST(APP_DOMAIN + "Newmethod/domic")
    @FormUrlEncoded
    Observable<BaseBean> agreeOrRefuseUpMicrophone(@Field("newtoken") String newtoken,
                                                   @Field("uid") String uid,
                                                   @Field("type") int type,
                                                   @Field("id") String id);

    /**
     * ??????????????????
     */
    @POST(APP_DOMAIN + "Newmethod/goodslist")
    @FormUrlEncoded
    Observable<GoodsListBean> getGoodsList(@Field("newtoken") String newtoken,
                                           @Field("uid") String uid);

    /**
     * ???????????????
     */
    //@POST(APP_DOMAIN + "tp6/public/index.php/index/Payapi/index")
    //@POST(APP_DOMAIN + "uppay")
    @POST(APP_DOMAIN_NoApi + "tp6/public/index.php/index/Payapi/index")
    @FormUrlEncoded
    Observable<PayBean> getRechargeOrder(@Field("paytype") String paytype,
                                           @Field("price") String price,
                                         @Field("goodsid") String goodsid,
                                         @Field("uid") String uid);

    /**
     * ???????????????
     */
    //@POST(APP_DOMAIN + "uppay")
    @POST(APP_DOMAIN_NoApi + "tp6/public/index.php/index/Payapi/index")
    @FormUrlEncoded
    Observable<Wxmodel> getRechargeWxOrder(@Field("paytype") String paytype,
                                         @Field("price") String price,
                                         @Field("goodsid") String goodsid,
                                         @Field("uid") String uid);

    /**
     * ???????????????
     */
    @POST(APP_DOMAIN + "Newmethod/exchange")
    @FormUrlEncoded
    Observable<BaseBean> newExchange(@Field("newtoken") String newtoken,
                                         @Field("uid") String uid,
                                         @Field("num") String num);

    /**
     * ????????????
     */
    @POST(APP_DOMAIN + "version")
    @FormUrlEncoded
    Observable<UpdateApkBean> checkVersion(@Field("type") String newtoken,
                                           @Field("version") String uid);

    /**
     * ????????????_??????????????????????????????
     */
    @POST(APP_DOMAIN + "getUserCost")
    @FormUrlEncoded
    Observable<BXUserCostBean> getUserCost(@Field("user_id") String user_id, @Field("type") int type);
}
