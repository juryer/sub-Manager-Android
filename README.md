SubManager (サブスク管理アプリ)
月額制サービス（サブスクリプション）の支払いを一括管理し、固定費を可視化するためのAndroidアプリケーションです。

📋 概要
「毎月いくら払っているか把握しきれていない」という課題を解決するために開発しました。シンプルな操作でサブスクを登録し、月間の合計金額を即座に確認できます。

✨ 主な機能
サブスクの一覧表示: 登録したサービス名と月額料金をカード形式で表示。

合計金額の自動計算: 登録データに基づき、月間の支払い合計額をリアルタイムで算出。

直感的な操作:

追加・編集: ダイアログや別画面から簡単にデータを入力。

削除: ゴミ箱アイコンから即座に不要なデータを削除（確認ダイアログ付き）。

並び替え: ドラッグ＆ドロップで表示順を自由に変更可能。

データ永続化: アプリを閉じてもデータが消えないよう、ローカルストレージへ保存。

🛠 使用技術
Language: Kotlin

UI Framework: Android Jetpack (ConstraintLayout, RecyclerView, MaterialCardView)

Data Persistence: SharedPreferences

Library: Gson (JSON形式でのデータシリアライズ)

Architecture: View-Adapter パターンによる効率的なリスト描画

🚀 こだわったポイント
ユーザー体験 (UX): ItemTouchHelper を実装し、直感的なドラッグ＆ドロップによる並び替え機能を実現しました。

データ整合性: リストの削除や並び替えが発生した際、即座に合計金額に反映されるようオブザーバー的な処理フローを意識しました。

デバッグ効率: Device Explorer や App Inspection を活用し、内部データ（JSON）の整合性を確認しながら開発を進めました。

📥 ダウンロード
[サブスク管理 v1.0 リリースページ]([https://github.com/juryer/[リポジトリ名]/releases/tag/v1.0](https://github.com/juryer/sub-Manager-Android/releases/tag/v1.0))
※実機にインストールして動作を確認いただけます。
