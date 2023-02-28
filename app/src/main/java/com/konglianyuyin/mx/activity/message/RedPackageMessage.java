package com.konglianyuyin.mx.activity.message;

import android.os.Parcel;

import com.umeng.socialize.utils.SLog;

import java.nio.charset.StandardCharsets;
import org.json.JSONException;
import org.json.JSONObject;
import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

@MessageTag(value = "RCD:HongBaoMsg", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class RedPackageMessage extends MessageContent {
    private String content;
    public RedPackageMessage(Parcel in) {
        content = ParcelUtils.readFromParcel(in);
    }

    public RedPackageMessage(byte[] data) {
        String jsonStr = null;
        try {
            jsonStr = new String(data, StandardCharsets.UTF_8);
            JSONObject jsonObj = new JSONObject(jsonStr);
            content = jsonObj.optString("content");
        } catch (Exception e) {
//            SLog.e(LogTag.IM, "PokeMessage parse error:" + e.toString());
        }
    }

    private RedPackageMessage() {}

    public String getContent() {
        return content;
    }

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("content", this.content);
            return jsonObj.toString().getBytes(StandardCharsets.UTF_8);
        } catch (JSONException e) {
            e.printStackTrace();
//            SLog.e(LogTag.IM, "PokeMessage encode error:" + e.toString());
        }

        return null;
    }

    public static RedPackageMessage obtain(String content) {
        RedPackageMessage pokeMessage = new RedPackageMessage();
        pokeMessage.content = content;
        return pokeMessage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, this.content);
    }

    public static final Creator<RedPackageMessage> CREATOR =
            new Creator<RedPackageMessage>() {
                public RedPackageMessage createFromParcel(Parcel source) {
                    return new RedPackageMessage(source);
                }

                public RedPackageMessage[] newArray(int size) {
                    return new RedPackageMessage[size];
                }
            };
}
