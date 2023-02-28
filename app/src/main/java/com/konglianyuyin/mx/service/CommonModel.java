/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.konglianyuyin.mx.service;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.konglianyuyin.mx.base.UserManager;
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

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

/**
 * ================================================
 * 展示 Model 的用法
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#2.4.3">Model wiki 官方文档</a>
 * Created by JessYan on 09/04/2016 10:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
@ActivityScope
public class CommonModel extends com.jess.arms.mvp.BaseModel {
    public static final int USERS_PER_PAGE = 10;

    @Inject
    public CommonModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    public Observable<Login> register(String phone,
                                         @Field("sex") String sex,
                                         @Field("birthday") String birthday,
                                         String nickname,
                                         String headimgurl,
                                         String system,
                                         String channel,
                                         @Field("code") String code,
                                         @Field("pusername") String pusername) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .register(phone, sex, birthday, nickname, headimgurl, system, channel, code,pusername);
    }

    public Observable<CodeBean> verification(String phone, String type) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .verification(phone, type);
    }

    public Observable<Login> login(String phone, String code) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .login(phone, code);
    }

    /**
     *新增账号密码登录
     * @param phone
     * @param pass
     * @return
     */
    public Observable<Login> loginPass(String phone, String pass) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .loginPass(phone, pass);
    }

    public Observable<Register> is_verification(String phone, String code) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .is_verification(phone, code);
    }

    public Observable<RecommenRoomBean> getrecommendroom(int categories, int page) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .getrecommendroom(categories, page);
    }

    public Observable<CategorRoomBean> room_categories() {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .room_categories();
    }

    public Observable<ZengSongBean> zengsong(String goto_uid, String money,String token) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .zengsong(goto_uid, money,token);
    }

    public Observable<ZengSongHisBean> zhengsonglog(String page) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .zhengsonglog(page);
    }

    public Observable<RecommenRoomBean> is_popular() {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .is_popular();
    }

    public Observable<RecommenRoomBean> secret_chat() {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .secret_chat();
    }

    public Observable<StartLoftBean> star_loft(int sex) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .star_loft(sex);
    }

    public Observable<BannerBean> carousel(String xx) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .carousel(xx);
    }

    public Observable<Register> create_or_edit_room(
            @Field("room_pass") String room_pass,
            @Field("room_name") String room_name,
            @Field("room_type") String room_type,
            @Field("room_intro") String room_intro,
            String room_background,
            String uid,
            String room_covere) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .create_or_edit_room(room_pass, room_name, room_type,
                        room_intro, room_background, uid, room_covere);
    }

    public Observable<RoomType> room_type(String xx) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .room_type(xx);
    }

    public Observable<RoomBg> room_background(String xx) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .room_background(xx);
    }

    public Observable<GiftListBean> gift_list(String user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .gift_list(user_id);
    }

    public Observable<Microphone> microphone_status(@Field("uid") String uid) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .microphone_status(uid);
    }

    public Observable<UpVideoResult> up_microphone(@Field("uid") String uid,
                                                   @Field("user_id") String user_id,
                                                   @Field("position") String position) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .up_microphone(uid, user_id, position);
    }


    public Observable<BaseBean> go_microphone(@Field("uid") String uid,
                                              @Field("user_id") String user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .go_microphone(uid, user_id);
    }

    public Observable<EnterRoom> enter_room(@Field("uid") String uid,
                                            @Field("room_pass") String room_pass,
                                            @Field("user_id") String user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .enter_room(uid, room_pass, user_id);
    }

    public Observable<BaseBean> upload_music(@Field("singer") String singer,
                                             @Field("music") String music,
                                             @Field("music_name") String music_name,
                                             @Field("upload_name") String upload_name) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .upload_music(singer, music, music_name, upload_name);
    }

    public Observable<DynamicDetailsBean> dynamic_details(@Field("user_id") String user_id,
                                                          @Field("sort") String sort,
                                                          @Field("page") String page,
                                                          @Field("id") String id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .dynamic_details(user_id, sort, page, id);
    }

    public Observable<BaseBean> send_dynamic(@Part("user_id") int user_id,
                                             @QueryMap Map<String, Object> op,
                                             @Part MultipartBody.Part audioFile,
                                             @Part MultipartBody.Part videoFile,
                                             @Part MultipartBody.Part img1File,
                                             @Part MultipartBody.Part img2File,
                                             @Part MultipartBody.Part img3File,
                                             @Part MultipartBody.Part img4File,
                                             @Part MultipartBody.Part img5File,
                                             @Part MultipartBody.Part img6File) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .send_dynamic(user_id, op, audioFile, videoFile, img1File, img2File, img3File, img4File, img5File, img6File);
    }

    public Observable<BaseBean> dynamic_comment(@Field("b_dynamic_id") int b_dynamic_id,
                                                @Field("user_id") int user_id,
                                                @Field("content") String content) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .dynamic_comment(b_dynamic_id, user_id, content);
    }

    public Observable<BaseBean> dynamic_collection(@Field("b_dynamic_id") int b_dynamic_id,
                                                   @Field("user_id") int user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .dynamic_collection(b_dynamic_id, user_id);
    }

    public Observable<DynamicSearchBean> dynamic_search(@Field("search") int search) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .dynamic_search(search);
    }

    public Observable<RecommendedDynamicBean> recommended_dynamic(@Field("user_id") String userId,
                                                                  @Field("page") String page) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .recommended_dynamic(userId, page);
    }

    public Observable<RecommendedDynamicBean> new_dynamic(@Field("user_id") String user_id,
                                                          @Field("page") String page) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .new_dynamic(user_id, page);
    }

    public Observable<TopicBean> topic(@Field("type") String type) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .topic(type);
    }

    public Observable<TopicDynamicBean> topic_dynamic(@Field("tags") String tags,
                                                      @Field("user_id") String user_id,
                                                      @Field("page") String page,
                                                      @Field("type") String type) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .topic_dynamic(tags, user_id, page, type);
    }


    public Observable<BaseBean> dynamic_praise(@Field("user_id") String user_id,
                                               @Field("target_id") String target_id,
                                               @Field("type") String type,
                                               @Field("hand") String hand) {

        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .dynamic_praise(user_id, target_id, type, hand);
    }

    public Observable<BaseBean> comment_praise(@Field("b_dynamic_id") int b_dynamic_id,
                                               @Field("user_id") int user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .comment_praise(b_dynamic_id, user_id);
    }

    public Observable<RecommenRoomBean> is_top(String xx) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .is_top(xx);
    }

    public Observable<BaseBean> is_pass(String uid) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .is_pass(uid);
    }

    public Observable<BaseBean> shut_microphone(String uid, int position) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .shut_microphone(uid, position);
    }

    public Observable<BaseBean> open_microphone(String uid, int position) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .open_microphone(uid, position);
    }

    public Observable<OtherUser> get_other_user(@Field("uid") String uid,
                                                @Field("user_id") String user_id,
                                                @Field("my_id") String my_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .get_other_user(uid, user_id, my_id);
    }


    public Observable<BaseBean> shut_sound(String uid, int position) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .shut_sound(uid, position);
    }

    public Observable<JinSheng> is_sound(String uid, String is_sound) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .is_sound(uid, is_sound);
    }


    public Observable<JinSheng> remove_sound(String uid, String is_sound) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .remove_sound(uid, is_sound);
    }

    public Observable<JinSheng> is_black(String uid, String is_sound) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .is_black(uid, is_sound);
    }

    public Observable<BaseBean> room_mykeep(String uid, String is_sound) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .room_mykeep(uid, is_sound);
    }

    public Observable<BaseBean> remove_mykeep(String uid, String is_sound) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .remove_mykeep(uid, is_sound);
    }

    public Observable<Rank> leaderboard(@Field("class") String type,
                                        @Field("type") String date,
                                        @Field("user_id") String user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .leaderboard(type, date, user_id);
    }

    public Observable<BaseBean> is_admin(String uid, String is_sound) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .is_admin(uid, is_sound);
    }

    public Observable<BaseBean> remove_admin(String uid, String is_sound) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .remove_admin(uid, is_sound);
    }

    public Observable<BaseBean> quit_room666666666(String id,
                                                   String room) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .quit_room666666666(id, room);
    }

    public Observable<BaseBean> quit_room(@Field("uid") String uid,
                                          @Field("followed_user_id") String followed_user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .quit_room(uid, followed_user_id);
    }

    public Observable<BaseBean> follow(@Field("uid") String uid,
                                       @Field("followed_user_id") String followed_user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .follow(uid, followed_user_id);
    }

    public Observable<BaseBean> cancel_follow(@Field("uid") String uid,
                                              @Field("followed_user_id") String followed_user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .cancel_follow(uid, followed_user_id);
    }

    public Observable<AdminUser> getRoomUsers(@Field("uid") String uid) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .getRoomUsers(uid);
    }

    public Observable<SearchAdmin> search_user(String uid,
                                               String followed_user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .search_user(uid, followed_user_id);
    }

    public Observable<BaseBean> friend_list(String user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .friend_list(user_id);
    }

    public Observable<BaseBean> not_speak_status(@Field("uid") String uid,
                                                 @Field("followed_user_id") String followed_user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .not_speak_status(uid, followed_user_id);
    }

    public Observable<MusicYinxiao> get_sound(String xx, String user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .get_sound(xx, user_id);
    }

    public Observable<Yinxiao> get_music(String singer,
                                         String music_name, String user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .get_music(singer, music_name, user_id);
    }

    public Observable<EmojiBean> emoji_list(String xx) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .emoji_list(xx);
    }

    public Observable<GifBean> get_emoji(String id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .get_emoji(id);
    }

    /**
     *
     * @param id  礼物id
     * @param uid 房主id
     * @param user_id  接收者id
     * @param fromUid  发送者id
     * @param type  类型
     * @param num  数量
     * @return
     */
    public Observable<SendGemResult> gift_queue(@Field("id") String id,
                                                @Field("uid") String uid,
                                                @Field("user_id") String user_id,
                                                @Field("fromUid") String fromUid,
                                                @Field("type") String type,
                                                @Field("num") String num) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .gift_queue(id, uid, user_id, fromUid, type,num, UserManager.getUser().getToken());
    }

    public Observable<WaitList> addWaid(String uid,
                                        String user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .addWaid(uid, user_id);
    }

    public Observable<AllRoomBean> getAllRoom() {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .getAllRoom();
    }

    public Observable<WaitList> waitList(String uid,
                                         String user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .waitList(uid, user_id);
    }

    public Observable<BaseBean> delWait(String user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .delWait(user_id);
    }

    public Observable<GoodsList> goodsList(String user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .goodsList(user_id);
    }

    public Observable<RecommendedDynamicBean> get_GZ_dynamic(@Field("user_id") String user_id,
                                                             @Field("page") String page) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).get_GZ_dynamic(user_id, page);
    }

    public Observable<MeYiDuiBean> getMeYiDui(@Field("user_id") String user_id,
                                              @Field("my_id") String my_id,
                                              @Field("type") String type,
                                              @Field("page") String page) {

        return mRepositoryManager.obtainRetrofitService(CommonService.class).getMeYiDui(user_id, my_id, type, page);
    }

    public Observable<UnreadBean> getUnreadMessage(@Field("user_id") String user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getUnreadMessage(user_id);
    }

    public Observable<MessageYoBean> getMessageYo(@Field("user_id") String user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getMessageYo(user_id);
    }

    public Observable<LookCommentBean> getLookComment(@Field("user_id") String user_id,
                                                      @Field("hf_uid") String hf_uid,
                                                      @Field("id") String id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getLookComment(user_id, hf_uid, id);
    }

    public Observable<AllCommentBean> getAlComment(@Field("id") String id,
                                                   @Field("user_id") String user_id,
                                                   @Field("page") String page) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getAllComment(id, user_id, page);
    }

    public Observable<CommentBean> setComment(@Field("id") String id,
                                              @Field("pid") String pid,
                                              @Field("user_id") String user_id,
                                              @Field("content") String content) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).setComment(id, pid, user_id, content);
    }

    public Observable<UserFriend> userFriend(@Field("user_id") String user_id,
                                             @Field("type") String type,
                                             @Field("page") String page) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                userFriend(user_id, type, page);
    }

    public Observable<UserBean> get_user_info(@Field("user_id") String user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).get_user_info(user_id);
    }


    public Observable<PayBean> rechargePay(@Field("user_id") String user_id,
                                           @Field("goods_id") String goods_id,
                                           @Field("type") String type) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                rechargePay(user_id, goods_id, type);
    }

    public Observable<Wxmodel> rechargeWxPay(@Field("user_id") String user_id,
                                             @Field("goods_id") String goods_id,
                                             @Field("type") String type) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                rechargeWxPay(user_id, goods_id, type);
    }

    public Observable<RecommendLabelBean> getLabel(@Field("xx") String xx) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getLabel(xx);
    }

    public Observable<SearchLabelBean> getSouSuoLabel(@Field("keywords") String keywords) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getSouSuoLabel(keywords);

    }

    public Observable<BaseBean> pull_black(@Field("user_id") String user_id,
                                           @Field("from_uid") String from_uid) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                pull_black(user_id, from_uid);
    }

    public Observable<BaseBean> cancel_black(@Field("user_id") String user_id,
                                             @Field("from_uid") String from_uid) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                cancel_black(user_id, from_uid);
    }

    public Observable<UserFriend> blackList(@Field("user_id") String user_id,
                                            @Field("page") String page) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                blackList(user_id, page);
    }

    public Observable<ReportBean> report(@Field("xx") String xx) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                report(xx);
    }

    public Observable<BaseBean> send_report(@Field("user_id") String user_id,
                                            @Field("img") String img,
                                            @Field("type") String type,
                                            @Field("target") String target,
                                            @Field("report_type") String report_type) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                send_report(user_id, img, type, target, report_type);
    }

    public Observable<OffiMessageBean> official_message(@Field("user_id") String user_id, String page) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).official_message(user_id, page);
    }

    public Observable<BaseBean> clear_message(@Field("user_id") String user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                clear_message(user_id);
    }

    public Observable<MiniOfficBean> mini_official(@Field("user_id") String user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                mini_official(user_id);
    }

    public Observable<BaseBean> cleargiftpricecount(String user_id, String room_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                cleargiftpricecount(user_id, room_id);
    }

    public Observable<MoneyBean> my_store(@Field("user_id") String user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                my_store(user_id);
    }

    public Observable<CashHis> tixian_log(@Field("user_id") String user_id, String page) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                tixian_log(user_id, page);
    }

    public Observable<CashHis> exchange_log(@Field("user_id") String user_id, String page) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                exchange_log(user_id, page);
    }

    public Observable<BaseBean> exchange(@Field("user_id") String user_id, String money) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                exchange(user_id, money);
    }

    public Observable<BindBean> ali_oauth_code(String xx) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                ali_oauth_code(xx);
    }

    public Observable<MyPersonalCebterBean> getPersonalCabter(@Field("user_id") String user_id,
                                                              @Field("from_uid") String from_uid) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getPersonalCabter(user_id, from_uid);
    }

    public Observable<AliInfor> ali_oauth_token(String xx, String user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                ali_oauth_token(xx, user_id);
    }

    public Observable<BaseBean> tixian(@Field("user_id") String user_id, String money) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                tixian(user_id, money);
    }

    public Observable<PayBean> sfrz_start(@Field("user_id") String user_id,
                                          @Field("name") String name,
                                          @Field("idno") String idno) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                sfrz_start(user_id, name, idno);
    }

    public Observable<BaseBean> bindIdentity(
            @Field("name") String name,
            @Field("idno") String idno) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                bindIdentity(name, idno);
    }

    public Observable<BaseBean> upalipay(String name, String idno) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                upalipay(name, idno);
    }


    public Observable<BaseBean> sfrz_query(@Field("user_id") String user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                sfrz_query(user_id);
    }

    public Observable<CommentBean> ForGetPWD(@Field("phone") String phone,
                                             @Field("code") String code,
                                             @Field("pass") String pass) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).ForGetPWD(phone, code, pass);
    }

    public Observable<ConstellationBean> getConstellation(@Field("birthday") String birthday) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getConstellation(birthday);
    }

    public Observable<OtherBean> setUserInfo(@Field("id") String id,
                                             @Field("img") String img,
                                             @Field("nickname") String nickname,
                                             @Field("sex") String sex,
                                             @Field("birthday") String birthday,
                                             @Field("constellation") String constellation,
                                             @Field("city") String city) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).setUserInfo(id, img, nickname, sex,
                birthday, constellation, city);
    }

    public Observable<AgreementBean> getAgreement(@Field("type") String type) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getAgreement(type);
    }

    public Observable<VipCenterBean> getVipCenter(@Field("user_id") String user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getVipCenter(user_id);
    }

    public Observable<DengJiBean> getLevelCenter(@Field("user_id") String user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getLevelCenter(user_id);
    }


    public Observable<IncomeBean> user_income(@Field("user_id") String user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                user_income(user_id);
    }

    public Observable<SearchHis> searhList(@Field("user_id") String user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                searhList(user_id);
    }

    public Observable<BaseBean> cleanSarhList(@Field("user_id") String user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                cleanSarhList(user_id);
    }

    public Observable<Search> merge_search(@Field("user_id") String user_id, String keywords) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                merge_search(user_id, keywords);
    }

    public Observable<UserFriend> search_all(@Field("user_id") String user_id,
                                             @Field("keywords") String keywords,
                                             @Field("type") String type,
                                             @Field("page") String page) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                search_all(user_id, keywords, type, page);
    }

    public Observable<RecommenRoomBean> search_all_room(@Field("user_id") String user_id,
                                                        @Field("keywords") String keywords,
                                                        @Field("type") String type,
                                                        @Field("page") String page) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                search_all_room(user_id, keywords, type, page);
    }

    public Observable<RecommendedDynamicBean> search_all_dyni(@Field("user_id") String user_id,
                                                              @Field("keywords") String keywords,
                                                              @Field("type") String type,
                                                              @Field("page") String page) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                search_all_dyni(user_id, keywords, type, page);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    public Observable<RoomUsersBean> getRoomUsers(String uid,
                                                  String user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                getRoomUsers(uid, user_id);
    }

    public Observable<SendGemResult> send_baoshi(String id,
                                                 String uid,
                                                 String token,
                                                 String fromUid,
                                                 String num) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                send_baoshi(id, uid, token, fromUid, num);
    }

    public Observable<SendGemResult> send_byk(
            String uid,
            String token,
            String fromUid,
            String num) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                send_byk(uid, token, fromUid, num);
    }

    public Observable<AgreeCpResult> handle_cp(
            String token,
            String user_id,
            String type) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                handle_cp(token, user_id, type);
    }

    public Observable<BaseBean> delete_cp(String token) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).
                delete_cp(token);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////

    public Observable<CollectionRoomListBean> getCollectionRoomList(@Field("user_id") String user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getCollectionRoomList(user_id);
    }

    public Observable<Login> registerOther(@Field("type") String type,
                                           @Field("openid") String openid,
                                           @Field("phone") String phone,
                                           @Field("sex") String sex,
                                           @Field("birthday") String birthday,
                                           @Field("nickname") String nickname,
                                           @Field("headimgurl") String headimgurl,
                                           @Field("pass") String pass,
                                           @Field("system") String system,
                                           @Field("channel") String channel,
                                           @Field("code") String code) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).registerOther(type, openid, phone, sex, birthday, nickname, headimgurl, pass, system, channel, code);
    }

    public Observable<OtherBean> logOther(@Field("openid") String openid,
                                          @Field("type") String type) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).logOther(openid, type);
    }

    public Observable<CommentBean> delCommunity(String user_id, String id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).delCommunity(user_id, id);
    }

    public Observable<BaseBean> feedBack(@Field("user_id") String user_id,
                                         @Field("content") String content,
                                         @Field("img") String img) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).feedBack(user_id, content, img);
    }

    public Observable<ActiveListBean> activeList(@Field("xx") String xx) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).activeList(xx);
    }

    public Observable<VipBean> get_user_vip(String uId, String token) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).get_user_vip(uId, token);
    }

    public Observable<CommentBean> fenxiang(@Field("user_id") String user_id,
                                            @Field("target_id") String target_id,
                                            @Field("hand") String hand) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).fenxiang(user_id, target_id, hand);
    }

    public Observable<BaseBean> out_room(@Field("uid") String uid,
                                         @Field("user_id") String user_id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).out_room(uid, user_id);
    }

    public Observable<GuanFangLianXiBean> guanfang(@Field("xx") String xx) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).guanfang(xx);
    }

    public Observable<BaseBean> UntyingAli(@Field("xx") String xx) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).UntyingAli(xx);
    }

    public Observable<EnterRoom> getRoomInfo(@Field("xx") String xx) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getRoomInfo(xx);
    }

    public Observable<ChaMoneyBean> getMoney(@Field("money") String money) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getMoney(money);
    }

    public Observable<FollowBean> is_follow(@Field("user_id") String user_id,
                                            @Field("from_uid") String from_uid) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).is_follow(user_id, from_uid);
    }

    public Observable<MyPackBean> my_pack(@Field("type") String type) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).my_pack(type);
    }

    public Observable<CommentBean> dress_up(@Field("id") String id,
                                            @Field("type") String type) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).dress_up(id, type);
    }

    public Observable<CPDetailsBean> cp_desc(@Field("id") String id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).cp_desc(id);
    }

    public Observable<CommentBean> remove_cp(@Field("id") String id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).remove_cp(id);
    }

    public Observable<BaoXiangBean> getBoxInfo(@Field("xx") String xx) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getBoxInfo(xx);
    }

    public Observable<OpenBoxBean> getAwardList(@Field("keysNum") int keysNum, @Field("bs") String bs) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getAwardList(keysNum, bs);
    }

    public Observable<CommentBean> actionBuyKeys(@Field("keysNum") String keysNum) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).actionBuyKeys(keysNum);
    }

    public Observable<XuYaoMiZuanBean> getMizuanNum(@Field("keysNum") String keysNum) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getMizuanNum(keysNum);
    }

    public Observable<BoxExchangeBean> getAwardWaresList(@Field("xx") String xx) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getAwardWaresList(xx);
    }

    public Observable<CommentBean> actionAwardExchange(@Field("waresId") String waresId) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).actionAwardExchange(waresId);
    }

    public Observable<BoxOpenRecordBean> getAwardRecordList(@Field("page") int page) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getAwardRecordList(page);
    }

    public Observable<BoxJiangChiBean> getAwardBoxList(String box_type) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getAwardBoxList(box_type);
    }

    public Observable<BXShuoMingTextBean> getRewardInfo(@Field("xx") String xx) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getRewardInfo(xx);
    }

    public Observable<CommentBean> openCPCard(@Field("xx") String xx) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).openCPCard(xx);
    }

    public Observable<CommentBean> exchangeMizuanCard(@Field("id") String id) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).exchangeMizuanCard(id);
    }

    public Observable<RoomRankBean> room_ranking(@Field("uid") String uid,
                                                 @Field("class") String mclass,
                                                 @Field("type") String type) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).room_ranking(uid, mclass, type);
    }

    public Observable<ShareBean> getQrCode(@Field("newtoken") String newtoken,
                                           @Field("uid") String uid) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).get_qr_code(newtoken, uid);
    }

    public Observable<LotteryBean> lottery(@Field("newtoken") String newtoken,
                                           @Field("uid") String uid,
                                           @Field("pokernum") String pokernum,
                                           @Field("price") String price) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).lottery(newtoken, uid,pokernum,price);
    }

    public Observable<LotteryHistoryBean> lotteryHistory(@Field("newtoken") String newtoken,
                                                         @Field("uid") String uid,
                                                         @Field("page") int page) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).lotteryHistory(newtoken, uid,page);
    }

    public Observable<BaseBean> requestUpMicrophone(@Field("newtoken") String newtoken,
                                           @Field("uid") String uid,
                                           @Field("roomid") String roomid){
        return mRepositoryManager.obtainRetrofitService(CommonService.class).requestUpMicrophone(newtoken, uid,roomid);
    }

    public Observable<BaseBean> clearAllMlZ(@Field("newtoken") String newtoken,
                                                    @Field("uid") String uid,
                                                    @Field("roomid") String roomid){
        return mRepositoryManager.obtainRetrofitService(CommonService.class).clearAllMlZ(newtoken, uid,roomid);
    }

    public Observable<RequestUpMicrophoneBean> requestUpMicrophoneList(@Field("newtoken") String newtoken,
                                                                       @Field("uid") String uid,
                                                                       @Field("roomid") String roomid){
        return mRepositoryManager.obtainRetrofitService(CommonService.class).requestUpMicrophoneList(newtoken, uid,roomid);
    }

    public Observable<BaseBean> agreeOrRefuseUpMicrophone(@Field("newtoken") String newtoken,
                                                                       @Field("uid") String uid,
                                                                       @Field("type") int type,
                                                                       @Field("id") String id){
        return mRepositoryManager.obtainRetrofitService(CommonService.class).agreeOrRefuseUpMicrophone(newtoken, uid,type,id);
    }

    public Observable<GoodsListBean> getGoodsList(@Field("newtoken") String newtoken,
                                                  @Field("uid") String uid){
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getGoodsList(newtoken, uid);
    }

    public Observable<PayBean> getRechargeOrder(@Field("paytype") String paytype,
                                                  @Field("price") String price,
                                                @Field("goodsid") String goodsid,
                                                @Field("uid") String uid){
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getRechargeOrder(paytype, price,goodsid,uid);
    }

    public Observable<Wxmodel> getRechargeWxOrder(@Field("paytype") String paytype,
                                                @Field("price") String price,
                                                @Field("goodsid") String goodsid,
                                                @Field("uid") String uid){
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getRechargeWxOrder(paytype, price,goodsid,uid);
    }

    public Observable<BaseBean> newExchange(@Field("newtoken") String newtoken,
                                                @Field("uid") String uid,
                                                @Field("num") String num){
        return mRepositoryManager.obtainRetrofitService(CommonService.class).newExchange(newtoken, uid,num);
    }

    public Observable<UpdateApkBean> checkVersion(@Field("type") String type,
                                                  @Field("version") String version){
        return mRepositoryManager.obtainRetrofitService(CommonService.class).checkVersion(type, version);
    }

    public Observable<BXUserCostBean> getUserCost(@Field("user_id") String user_id, @Field("type") int type) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getUserCost(user_id, type);
    }
}
