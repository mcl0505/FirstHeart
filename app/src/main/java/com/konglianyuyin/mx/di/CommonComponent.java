package com.konglianyuyin.mx.di;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.konglianyuyin.mx.activity.AgreementActivity;
import com.konglianyuyin.mx.activity.ChargeActivity;
import com.konglianyuyin.mx.activity.EditGaoActivity;
import com.konglianyuyin.mx.activity.HelpActivity;
import com.konglianyuyin.mx.activity.MainActivity;
import com.konglianyuyin.mx.activity.SearchDynamicActivity;
import com.konglianyuyin.mx.activity.SearchHisActivity;
import com.konglianyuyin.mx.activity.SearchResultActivity;
import com.konglianyuyin.mx.activity.SearchRoomActivity;
import com.konglianyuyin.mx.activity.SearchUserActivity;
import com.konglianyuyin.mx.activity.SetActivity;
import com.konglianyuyin.mx.activity.dynamic.CommentDetailsActivity;
import com.konglianyuyin.mx.activity.dynamic.DynamicDetailsActivity;
import com.konglianyuyin.mx.activity.dynamic.DynamicNewsActivity;
import com.konglianyuyin.mx.activity.dynamic.HeatTopicActivity;
import com.konglianyuyin.mx.activity.dynamic.MeZanActivity;
import com.konglianyuyin.mx.activity.dynamic.SocialReleaseActivity;
import com.konglianyuyin.mx.activity.dynamic.TopicTrendsActivity;
import com.konglianyuyin.mx.activity.login.BingPhoneActivity;
import com.konglianyuyin.mx.activity.login.ForgetPsActivity;
import com.konglianyuyin.mx.activity.login.LoginActivity;
import com.konglianyuyin.mx.activity.login.ModifyPsActivity;
import com.konglianyuyin.mx.activity.login.RegisterActivity;
import com.konglianyuyin.mx.activity.login.SexActivity;
import com.konglianyuyin.mx.activity.login.UploadActivity;
import com.konglianyuyin.mx.activity.message.MessageActivity;
import com.konglianyuyin.mx.activity.mine.BindAliPayNewActivity;
import com.konglianyuyin.mx.activity.mine.BingCancelActivity;
import com.konglianyuyin.mx.activity.mine.GiveOtherActivity;
import com.konglianyuyin.mx.activity.mine.MyGiveHistoryActivity;
import com.konglianyuyin.mx.activity.mine.MyGiveMainActivity;
import com.konglianyuyin.mx.activity.mine.fragment.MoneyLeftFragment;
import com.konglianyuyin.mx.activity.mine.fragment.MoneyRightFragment;
import com.konglianyuyin.mx.activity.my.BlackListActivity;
import com.konglianyuyin.mx.activity.my.CPActivity;
import com.konglianyuyin.mx.activity.my.GradeCenterActivity;
import com.konglianyuyin.mx.activity.my.InviteAcvitity;
import com.konglianyuyin.mx.activity.my.MemberCoreActivity;
import com.konglianyuyin.mx.activity.my.ModifyDataActivity;
import com.konglianyuyin.mx.activity.my.MyFollowActivity;
import com.konglianyuyin.mx.activity.my.MyPackageActivity;
import com.konglianyuyin.mx.activity.my.MyPersonalCenterActivity;
import com.konglianyuyin.mx.activity.message.MessageOfficeActivity;
import com.konglianyuyin.mx.activity.message.MessageSetActivity;
import com.konglianyuyin.mx.activity.message.ReportActivity;
import com.konglianyuyin.mx.activity.mine.CashMoneyActivity;
import com.konglianyuyin.mx.activity.mine.MoneyActivity;
import com.konglianyuyin.mx.activity.mine.MyProfitActivity;
import com.konglianyuyin.mx.activity.mine.RealNameActivity;
import com.konglianyuyin.mx.activity.mine.WebRealNameActivity;
import com.konglianyuyin.mx.activity.room.AdminHomeActivity;
import com.konglianyuyin.mx.activity.room.CollectionRoomListActivity;
import com.konglianyuyin.mx.activity.room.LotteryActivity;
import com.konglianyuyin.mx.activity.room.LotteryHistoryActivity;
import com.konglianyuyin.mx.activity.room.MusicActivity;
import com.konglianyuyin.mx.activity.room.MyMusciActivity;
import com.konglianyuyin.mx.activity.room.RankActivity;
import com.konglianyuyin.mx.activity.room.RoomSettingActivity;
import com.konglianyuyin.mx.activity.room.SetAdminActivity;
import com.konglianyuyin.mx.fragment.BeiBaoHeadKuangFragment;
import com.konglianyuyin.mx.fragment.BeiBaoIntoSEFragment;
import com.konglianyuyin.mx.fragment.BeiBaoTalkKuangFragment;
import com.konglianyuyin.mx.fragment.BeiBaoTalkapertureFragment;
import com.konglianyuyin.mx.fragment.CPAFragment;
import com.konglianyuyin.mx.fragment.CPBFragment;
import com.konglianyuyin.mx.fragment.CPCFragment;
import com.konglianyuyin.mx.fragment.CardFragment;
import com.konglianyuyin.mx.fragment.CashHisFragment;
import com.konglianyuyin.mx.fragment.CommFragment;
import com.konglianyuyin.mx.fragment.DressUpFragment;
import com.konglianyuyin.mx.fragment.EmojiFragment;
import com.konglianyuyin.mx.fragment.FollowDynamicFragment;
import com.konglianyuyin.mx.fragment.GemFragment;
import com.konglianyuyin.mx.fragment.GemstoneFragment;
import com.konglianyuyin.mx.fragment.MainCenterFragment;
import com.konglianyuyin.mx.fragment.MainCommunityFragment;
import com.konglianyuyin.mx.fragment.MainFindFragment;
import com.konglianyuyin.mx.fragment.MainHomeFragment;
import com.konglianyuyin.mx.fragment.MainHomeNewFragment;
import com.konglianyuyin.mx.fragment.MainHomeRankFragmentNew;
import com.konglianyuyin.mx.fragment.MainMessage2Fragment;
import com.konglianyuyin.mx.fragment.MainMessageFragment;
import com.konglianyuyin.mx.fragment.MessageFansFragment;
import com.konglianyuyin.mx.fragment.MessageFragment;
import com.konglianyuyin.mx.fragment.MessageFriendFragment;
import com.konglianyuyin.mx.fragment.MyConcernFragment;
import com.konglianyuyin.mx.fragment.MyDongTaiFragment;
import com.konglianyuyin.mx.fragment.MyGiftFragment;
import com.konglianyuyin.mx.fragment.MyMusicFragment;
import com.konglianyuyin.mx.fragment.NetMuscicFragment;
import com.konglianyuyin.mx.fragment.NewestDynamicFragment;
import com.konglianyuyin.mx.fragment.PresentFragment;
import com.konglianyuyin.mx.fragment.RankFragment;
import com.konglianyuyin.mx.fragment.RankFragmentN;
import com.konglianyuyin.mx.fragment.RankFragmentNew;
import com.konglianyuyin.mx.fragment.RecomFragment;
import com.konglianyuyin.mx.fragment.RecomHomeFragment;
import com.konglianyuyin.mx.fragment.RecommendHomeFragment;
import com.konglianyuyin.mx.fragment.RoomRankFragment;
import com.konglianyuyin.mx.fragment.RoomRankFragmentNew;
import com.konglianyuyin.mx.fragment.TopicFragment;

import dagger.Component;

//import com.midiyuyin.mx.activity.ChargeActivity;

/**
 * 必须依赖arms包，dagger才会生效
 */
@ActivityScope
@Component(modules = CommonModule.class, dependencies = AppComponent.class)
public interface CommonComponent {
    //------Activity--------

    void inject(MainActivity activity);

    void inject(LoginActivity activity);

    void inject(RankFragmentNew rankFragmentNew);

    void inject(RoomRankFragmentNew roomRankFragmentNew);

    void inject(MoneyLeftFragment moneyLeftFragment);

    void inject(MoneyRightFragment moneyRightFragment);

    void inject(RegisterActivity activity);

    void inject(ForgetPsActivity activity);

    void inject(SexActivity activity);

    void inject(UploadActivity activity);

    void inject(RoomSettingActivity activity);

    void inject(AdminHomeActivity activity);

    void inject(SetActivity activity);

    void inject(EditGaoActivity activity);

    void inject(RankActivity activity);

    void inject(SocialReleaseActivity socialReleaseActivity);

    void inject(SetAdminActivity activity);

    void inject(MusicActivity activity);

    void inject(MyMusciActivity activity);

    void inject(HeatTopicActivity activity);

    void inject(TopicTrendsActivity topicTrendsActivity);

    void inject(DynamicDetailsActivity dynamicDetailsActivity);

    void inject(ChargeActivity dynamicDetailsActivity);

    void inject(DynamicNewsActivity dynamicNewsActivity);

    void inject(MeZanActivity meZanActivity);

    void inject(CommentDetailsActivity commentDetailsActivity);

    void inject(MessageActivity messageActivity);

    void inject(MessageSetActivity messageActivity);

    void inject(ReportActivity messageActivity);

    void inject(MessageOfficeActivity messageActivity);

    void inject(MoneyActivity messageActivity);

    void inject(MyPersonalCenterActivity myPersonalCenterActivity);

    void inject(CashMoneyActivity myPersonalCenterActivity);

    void inject(RealNameActivity myPersonalCenterActivity);

    void inject(BindAliPayNewActivity bindAliPayNewActivity);

    void inject(WebRealNameActivity myPersonalCenterActivity);

    void inject(ModifyDataActivity modifyDataActivity);

    void inject(ModifyPsActivity modifyPsActivity);

    void inject(BlackListActivity blackListActivity);

    void inject(AgreementActivity agreementActivity);

    void inject(MyProfitActivity agreementActivity);

    void inject(MyFollowActivity myFollowActivity);

    void inject(MemberCoreActivity memberCoreActivity);

    void inject(GradeCenterActivity gradeCenterActivity);

    void inject(SearchHisActivity gradeCenterActivity);

    void inject(SearchResultActivity gradeCenterActivity);

    void inject(SearchUserActivity gradeCenterActivity);

    void inject(SearchRoomActivity gradeCenterActivity);

    void inject(SearchDynamicActivity gradeCenterActivity);

    void inject(BingCancelActivity bingCancelActivity);

    void inject(CollectionRoomListActivity collectionRoomListActivity);

    void inject(BingPhoneActivity bingPhoneActivity);

    void inject(HelpActivity helpActivity);

    void inject(MyPackageActivity myPackageActivity);

    void inject(CPActivity cpActivity);


    void inject(GiveOtherActivity giveOtherActivity);

    void inject(MyGiveMainActivity myGiveMainActivity);

    void inject(MyGiveHistoryActivity myGiveHistoryActivity);

    //------Fragment--------

    void inject(RankFragmentN rankFragmentN);

    void inject(MainHomeFragment mainHomeFragment);
    void inject(MainHomeNewFragment mainHomeFragment);

    void inject(MainCommunityFragment mainHomeFragment);

    void inject(MainMessageFragment mainHomeFragment);

    void inject(MainMessage2Fragment mainHomeFragment);

    void inject(MainFindFragment mainHomeFragment);

    void inject(MainCenterFragment mainHomeFragment);

    void inject(MainHomeRankFragmentNew mainHomeFragment);

    void inject(RecomFragment recomFragment);

    void inject(RankFragment recomFragment);

    void inject(CommFragment commFragment);

    void inject(MyMusicFragment commFragment);

    void inject(NetMuscicFragment commFragment);

    void inject(TopicFragment topicFragment);

    void inject(EmojiFragment topicFragment);

    void inject(NewestDynamicFragment newestDynamicFragment);

    void inject(FollowDynamicFragment followDynamicFragment);

    void inject(MessageFragment followDynamicFragment);

    void inject(MessageFansFragment followDynamicFragment);

    void inject(MessageFriendFragment followDynamicFragment);

    void inject(CashHisFragment followDynamicFragment);

    void inject(MyGiftFragment myGiftFragment);

    void inject(MyDongTaiFragment myDongTaiFragment);

    void inject(MyConcernFragment myConcernFragment);

    void inject(GemstoneFragment mGemstoneFragment);

    void inject(DressUpFragment mDressUpFragment);

    void inject(GemFragment gemFragment);

    void inject(PresentFragment presentFragment);

    void inject(CardFragment cardFragment);

    void inject(BeiBaoHeadKuangFragment beiBaoHeadKuangFragment);

    void inject(BeiBaoTalkKuangFragment beiBaoTalkKuangFragment);

    void inject(BeiBaoIntoSEFragment beiBaoIntoSEFragment);

    void inject(BeiBaoTalkapertureFragment beiBaoTalkapertureFragment);

    void inject(CPAFragment cpaFragment);

    void inject(CPBFragment cpbFragment);

    void inject(CPCFragment cpcFragment);

    void inject(RecomHomeFragment recomFragment);

    void inject(RecommendHomeFragment recomFragment);

    void inject(RoomRankFragment roomRankFragment);

    void inject(InviteAcvitity inviteAcvitity);

    void inject(LotteryActivity lotteryActivity);

    void inject(LotteryHistoryActivity lotteryActivity);
}
