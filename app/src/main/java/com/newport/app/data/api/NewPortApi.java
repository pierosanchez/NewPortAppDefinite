package com.newport.app.data.api;

import com.newport.app.BuildConfig;
import com.newport.app.data.models.request.ChatRequest;
import com.newport.app.data.models.request.NewsLogRequest;
import com.newport.app.data.models.request.PhotoRequest;
import com.newport.app.data.models.request.SwitchScheduleEmailRequest;
import com.newport.app.data.models.request.UserRegisterRequest;
import com.newport.app.data.models.response.BoletasPagoResponse;
import com.newport.app.data.models.response.ChatChannelResponse;
import com.newport.app.data.models.response.ChatOwnerChatResponse;
import com.newport.app.data.models.response.ChatSendMessageResponse;
import com.newport.app.data.models.response.ChatUserChatResponse;
import com.newport.app.data.models.response.DirectoryResponse;
import com.newport.app.data.models.response.EventsResponse;
import com.newport.app.data.models.response.GenericResponse;
import com.newport.app.data.models.response.HomeResponse;
import com.newport.app.data.models.response.LoginResponse;
import com.newport.app.data.models.response.MatchsResponse;
import com.newport.app.data.models.response.NewResponse;
import com.newport.app.data.models.response.PhotoGalleryEventResponse;
import com.newport.app.data.models.response.PhotoLikeResponse;
import com.newport.app.data.models.response.PhotoUploadedResponse;
import com.newport.app.data.models.response.ScheduleResponse;
import com.newport.app.data.models.response.SwitchScheduleEmailResponse;
import com.newport.app.data.models.response.SwitchSchedulesPendingRequestResponse;
import com.newport.app.data.models.response.UserElectionResponse;
import com.newport.app.data.models.response.UserRegisterResponse;
import com.newport.app.data.models.response.UserResponse;
import com.newport.app.data.models.response.UserScheduleResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.newport.app.util.Constant.HEADER_AUTHORIZATION;

/**
 * Created by tohure on 06/11/17.
 */

public interface NewPortApi {
    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.LOGIN2)
    Call<LoginResponse> login(@Query("mail") String dni, @Query("password") String password, @Query("firebase_token") String token);

    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.LOGIN3)
    Call<LoginResponse> login3(@Query("mail") String dni, @Query("password") String password, @Query("firebase_token") String token);

    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.HOME)
    Call<HomeResponse> getHome(@Query("dni") String dni);

    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.USER)
    Call<UserResponse> getUser(@Query("dni") String dni);

    @Headers(HEADER_AUTHORIZATION)
    @PUT(BuildConfig.USERREGAPP)
    Call<UserRegisterResponse> userRegistrationToApp(@Body UserRegisterRequest userRegisterRequest);

    @Headers(HEADER_AUTHORIZATION)
    @PUT(BuildConfig.CHANGEPASSWORD)
    Call<UserRegisterResponse> changeUserPassword(@Body UserRegisterRequest userRegisterRequest);

    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.EVENTS)
    Call<List<EventsResponse>> getEvents();

    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.NEW_DETAIL)
    Call<NewResponse> getNewDetail(@Path("id") int idNew);

    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.GALLERY_PHOTOS_EVENT)
    Call<List<PhotoGalleryEventResponse>> getPhotosEvent(@Path("id") int idNew);

    @Headers(HEADER_AUTHORIZATION)
    @POST(BuildConfig.SEND_PHOTO)
    Call<PhotoUploadedResponse> sendPhoto(@Body PhotoRequest photo);

    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.DIRECTORY)
    Call<List<DirectoryResponse>> getDirectory();

    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.SCHEDULES)
    Call<List<ScheduleResponse>> getSchedules();

    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.USERSCHEDULES)
    Call<UserScheduleResponse> getUserSchedules(@Path("sap_code") String sap_code);

    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.PHOTOLIKE)
    Call<PhotoLikeResponse> setPhotoLike(@Path("photoId") int photoId, @Path("userDni") String userDni);

    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.COUNTLIKES)
    Call<PhotoLikeResponse> getPhotoLikes(@Path("photoId") int photoId);

    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.PHOTOLIKEDBY)
    Call<PhotoLikeResponse> getPhotoLikedBy(@Path("photoId") int photoId, @Path("userDni") String userDni);

    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.MATCHS)
    Call<List<MatchsResponse>> getMatchs();

    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.VALIDATEUSERELECTION)
    Call<UserElectionResponse> validateUserElection(@Path("dni") String dni, @Path("idpartido") String idpartido);

    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.USERELECTION)
    Call<UserElectionResponse> setUserElection(@Path("dni") String dni, @Path("election1") String election1, @Path("election2") String election2,
                                               @Path("election3") String election3, @Path("election4") String election4, @Path("id_partido1") String id_partido1,
                                               @Path("id_partido2") String id_partido2, @Path("id_partido3") String id_partido3, @Path("id_partido4") String id_partido4);

    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.DATASCHEDULE)
    Call<UserResponse> getScheduleData(@Path("dni") String dni);

    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.USERSOFF)
    Call<List<UserScheduleResponse>> getUsersOff(@Path("day_off") String day_off);

    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.USERSWORK)
    Call<List<UserScheduleResponse>> getUserWork(@Path("day_work") String day_work);

    @Headers(HEADER_AUTHORIZATION)
    @POST(BuildConfig.USERSENDEMAILSWITCHSCHEDULE)
    Call<SwitchScheduleEmailResponse> sendEmailSwitchSchedule(@Body SwitchScheduleEmailRequest switchScheduleEmailRequest);

    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.USERSWITCHSCHEDULESPENDINGREQUEST)
    Call<List<SwitchSchedulesPendingRequestResponse>> getUserSwitchSchedulePendingRequests(@Path("user_mail") String user_mail);

    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.BOSSSWITCHSCHEDULESPENDINGREQUEST)
    Call<List<SwitchSchedulesPendingRequestResponse>> getBossUserSwitchSchedulePendingRequests(@Path("boss_email") String boss_email);

    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.MANAGERSWITCHSCHEDULESPENDINGREQUEST)
    Call<List<SwitchSchedulesPendingRequestResponse>> getManagerUserSwitchSchedulePendingRequests(@Path("managers_email") String managers_email);

    @Headers(HEADER_AUTHORIZATION)
    @PUT(BuildConfig.USERSENDEMAILCOWOSWITCHSCHEDULE)
    Call<SwitchScheduleEmailResponse> sendMailCoWoSwitchSchedule(@Body SwitchScheduleEmailRequest switchScheduleEmailRequest);

    @Headers(HEADER_AUTHORIZATION)
    @PUT(BuildConfig.USERSENDEMAILBOSSSWITCHSCHEDULE)
    Call<SwitchScheduleEmailResponse> sendMailBossSwitchSchedule(@Body SwitchScheduleEmailRequest switchScheduleEmailRequest);

    @Headers(HEADER_AUTHORIZATION)
    @PUT(BuildConfig.USERSENDEMAILMANAGERSWITCHSCHEDULE)
    Call<SwitchScheduleEmailResponse> sendMailManagerSwitchSchedule(@Body SwitchScheduleEmailRequest switchScheduleEmailRequest);

    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.USERSCHEDULEBYNAME)
    Call<UserScheduleResponse> getUserScheduleByName(@Path("user_name") String user_name);

    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.BOLETASPAGO)
    Call<BoletasPagoResponse> getBoletasPago(@Path("sap_code") String sap_code);

    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.VALIDATEACCESSBOLETASPAGO)
    Call<BoletasPagoResponse> validateAccessBoletasPago(@Path("sap_code") String sap_code, @Path("password_user") String password_user);

    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.VERIFICATIONUSERALLOWBOLETAPAGO)
    Call<BoletasPagoResponse> verificationUserAllowBoletaPagoPresenter(@Path("sap_code") String sap_code);

    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.CHATOWNERMESSAGES)
    Call<ChatOwnerChatResponse> getOwnerChatMessages(@Path("chat_id") int chat_id, @Path("owner_id") String owner_id);

    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.CHATUSERMESSAGES)
    Call<ChatUserChatResponse> getUserChatMessages(@Path("chat_id") int chat_id, @Path("user_id") String user_id);

    @Headers(HEADER_AUTHORIZATION)
    @GET(BuildConfig.CHATCHANNEL)
    Call<List<ChatChannelResponse>> getChannels(@Path("user_id") String cod_sap);

    @Headers(HEADER_AUTHORIZATION)
    @POST(BuildConfig.CHATSENDMESSAGE)
    Call<ChatSendMessageResponse> sendMessage(@Body ChatRequest chatRequest);

    @Headers(HEADER_AUTHORIZATION)
    @POST(BuildConfig.NEWSLOG)
    Call<GenericResponse> saveNewsLog(@Body NewsLogRequest newsLogRequest);
}




























