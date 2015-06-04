Feature: Order feature

  Scenario: 商品を一個注文（値引きなし）すると、次の注文画面に遷移する
    Then I wait for the view with id "MainMenuActivity" to appear
    When I press view with id "SyncButton"
	Then I wait for dialog to close
	When I press view with id "RegisterButton"
	Then I wait for the view with id "category_list" to appear
	When I press view with id "plusButton"
	When I press view with id "ok_button"
	Then I wait for the view with id "RegisterConfirmActivity" to appear
	When I press view with id "ok_button"
	Then I wait for the view with id "category_list" to appear
	
  Scenario: 商品を一個注文（値引きあり）すると、次の注文画面に遷移する
    Then I wait for the view with id "MainMenuActivity" to appear
    When I press view with id "SyncButton"
  	Then I wait for dialog to close
  	When I press view with id "RegisterButton"
  	Then I wait for the view with id "category_list" to appear
  	When I press view with id "plusButton"
  	When I press view with id "ok_button"
  	Then I wait for the view with id "RegisterConfirmActivity" to appear
  	When I enter text "5" into field with id "discountValue"
  	When I press view with id "ok_button"
  	Then I wait for the view with id "category_list" to appear
	
  Scenario: 商品を一個注文しようとするが、確認画面でキャンセルし、注文画面に遷移する
    Then I wait for the view with id "MainMenuActivity" to appear
    When I press view with id "SyncButton"
	Then I wait for dialog to close
	When I press view with id "RegisterButton"
	Then I wait for the view with id "category_list" to appear
	When I press view with id "plusButton"
	When I press view with id "ok_button"
	Then I wait for the view with id "RegisterConfirmActivity" to appear
	When I press view with id "cancel_button"
	Then I wait for the view with id "category_list" to appear
	
  Scenario: 商品を選択せずにOKボタンを押しても確認画面に遷移しない
    Then I wait for the view with id "MainMenuActivity" to appear
    When I press view with id "SyncButton"
  	Then I wait for dialog to close
  	When I press view with id "RegisterButton"
  	Then I wait for the view with id "category_list" to appear
  	When I press view with id "ok_button"
  	Then I wait for the view with id "category_list" to appear
	
  Scenario: SDカード同期をスキップした場合、注文画面に遷移しない
    Then I wait for the view with id "MainMenuActivity" to appear
	When I press view with id "RegisterButton"
	Then I wait for the view with id "MainMenuActivity" to appear
