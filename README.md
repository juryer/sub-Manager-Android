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

---
## スクリーンショット

<img src="https://github.com/user-attachments/assets/8e5a6d86-73d5-4362-affb-b15fc9e022bf" width="20%">
<img src="https://github.com/user-attachments/assets/3bd73fc1-1821-467e-99f2-8d7d0732ed42" width="20%">



