package com.dewire.dehub.view.login;

import static com.dewire.dehub.TestUtil.setRefWatcher;
import static com.dewire.dehub.TestUtil.setView;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import com.dewire.dehub.BaseTest;
import com.dewire.dehub.model.GistApi;
import com.dewire.dehub.util.ErrorIndicator;
import com.dewire.dehub.view.login.view.LoginContract;
import com.dewire.dehub.view.util.LoadingIndicator;
import com.squareup.leakcanary.RefWatcher;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;
import rx.subjects.BehaviorSubject;

public class LoginTest extends BaseTest {

  private LoginPresenter presenter;
  private LoginContract view;
  private GistApi api;

  /**
   * Setup.
   */
  @Before
  public void setup() {

    presenter = new LoginPresenter();
    view = makeViewMock();
    api = makeMockApi();

    presenter.api = api;
    setView(presenter, view);
    setRefWatcher(presenter, RefWatcher.DISABLED);
  }

  private LoginContract makeViewMock() {
    LoginContract view = mock(LoginContract.class,
        withSettings().extraInterfaces(LoadingIndicator.class, ErrorIndicator.class));

    when(view.usernameText()).thenReturn(Observable.just(""));
    when(view.passwordText()).thenReturn(Observable.just(""));
    when(view.loginButtonClick()).thenReturn(Observable.never());

    return view;
  }

  private GistApi makeMockApi() {
    GistApi api = mock(GistApi.class);
    when(api.login(anyString(), anyString())).thenReturn(Observable.just(null));
    return api;
  }

  @Test
  public void login_button_is_disabled_when_username_and_password_are_not_entered()
      throws Exception {

    when(view.usernameText()).thenReturn(Observable.just(""));
    when(view.passwordText()).thenReturn(Observable.just(""));

    presenter.onSubscribe(null);

    verify(view).enableLoginButton(false);
  }

  @Test
  public void login_button_is_disabled_when_only_username_entered()
      throws Exception {

    when(view.usernameText()).thenReturn(Observable.just("username"));
    when(view.passwordText()).thenReturn(Observable.just(""));

    presenter.onSubscribe(null);

    verify(view).enableLoginButton(false);
  }

  @Test
  public void login_button_is_disabled_when_only_password_entered()
      throws Exception {

    when(view.usernameText()).thenReturn(Observable.just(""));
    when(view.passwordText()).thenReturn(Observable.just("password"));

    presenter.onSubscribe(null);

    verify(view).enableLoginButton(false);
  }

  @Test
  public void login_button_is_enabled_when_username_and_password_are_entered() throws Exception {

    when(view.usernameText()).thenReturn(Observable.just("username"));
    when(view.passwordText()).thenReturn(Observable.just("password"));

    presenter.onSubscribe(null);

    verify(view).enableLoginButton(true);
  }

  @Test
  public void login_button_is_disabled_after_it_is_clicked() throws Exception {

    when(view.usernameText()).thenReturn(Observable.just("username"));
    when(view.passwordText()).thenReturn(Observable.just("password"));

    BehaviorSubject<Void> click = BehaviorSubject.create();
    when(view.loginButtonClick()).thenReturn(click);

    presenter.onSubscribe(null);

    click.onNext(null);

    verify(view).enableLoginButton(false);
  }

  @Test
  public void login_button_is_enabled_when_login_fails() throws Exception {

    when(view.usernameText()).thenReturn(Observable.just("username"));
    when(view.passwordText()).thenReturn(Observable.just("password"));

    BehaviorSubject<Void> click = BehaviorSubject.create();
    when(view.loginButtonClick()).thenReturn(click);

    when(api.login(anyString(), anyString())).thenReturn(Observable.error(new Exception()));

    presenter.onSubscribe(null);

    click.onNext(null);

    verify(view, never()).starAppActivity();
    verify(view).enableLoginButton(false);
  }

  @Test
  public void app_activity_is_started_after_successful_login() throws Exception {

    when(view.usernameText()).thenReturn(Observable.just("username"));
    when(view.passwordText()).thenReturn(Observable.just("password"));

    BehaviorSubject<Void> click = BehaviorSubject.create();
    when(view.loginButtonClick()).thenReturn(click);

    presenter.onSubscribe(null);

    click.onNext(null);

    verify(view).starAppActivity();
  }

}