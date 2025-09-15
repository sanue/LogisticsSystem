-- ===========================================
-- 物流システム データベース初期化スクリプト
-- Logistics System Database Initialization Script
-- ===========================================

-- 現在のユーザーを確認
-- Check current user
SELECT USER FROM DUAL;

-- ===========================================
-- シーケンス作成
-- Sequence Creation
-- ===========================================

-- 入庫シーケンス：入庫伝票の連番を自動生成
-- Incoming sequence: Auto-generate sequential numbers for incoming orders
CREATE SEQUENCE INCOMING_SEQ START WITH 1 INCREMENT BY 1;

-- 在庫シーケンス：在庫調整伝票の連番を自動生成
-- Inventory sequence: Auto-generate sequential numbers for inventory adjustments
CREATE SEQUENCE INVENTORY_SEQ START WITH 1 INCREMENT BY 1;

-- 出庫シーケンス：出庫伝票の連番を自動生成
-- Shipment sequence: Auto-generate sequential numbers for outgoing orders
CREATE SEQUENCE SHIPMENT_SEQ START WITH 1 INCREMENT BY 1;

-- 棚卸シーケンス：棚卸伝票の連番を自動生成
-- Stocktake sequence: Auto-generate sequential numbers for stocktaking
CREATE SEQUENCE STOCKTAKE_SEQ START WITH 1 INCREMENT BY 1;

-- ログシーケンス：システムログの連番を自動生成
-- Log sequence: Auto-generate sequential numbers for system logs
CREATE SEQUENCE LOG_SEQ START WITH 1 INCREMENT BY 1;

-- ===========================================
-- マスタテーブル作成
-- Master Table Creation
-- ===========================================

-- 商品マスタテーブル：商品の基本情報を管理
-- Product Master Table: Manages basic product information
CREATE TABLE PRODUCT_MASTER (
    PRODUCT_ID VARCHAR2(20) PRIMARY KEY,      -- 商品ID（主キー）：商品の一意識別子
    PRODUCT_NAME VARCHAR2(100) NOT NULL,      -- 商品名（必須）：商品の名称
    SPECIFICATION VARCHAR2(200),              -- 仕様：商品の詳細仕様や説明
    UNIT VARCHAR2(10) NOT NULL,               -- 単位（必須）：商品の計量単位（個、箱、kg等）
    SAFETY_STOCK NUMBER DEFAULT 0,            -- 安全在庫：在庫不足警告の閾値
    CREATED_AT DATE DEFAULT SYSDATE,          -- 作成日時：レコード作成時刻（自動設定）
    UPDATED_AT DATE DEFAULT SYSDATE           -- 更新日時：レコード更新時刻（自動設定）
);

-- 顧客マスタテーブル：顧客の基本情報を管理
-- Customer Master Table: Manages basic customer information
CREATE TABLE CUSTOMER_MASTER (
    CUSTOMER_ID VARCHAR2(20) PRIMARY KEY,     -- 顧客ID（主キー）：顧客の一意識別子
    CUSTOMER_NAME VARCHAR2(100) NOT NULL,     -- 顧客名（必須）：顧客の名称
    ADDRESS VARCHAR2(200),                    -- 住所：顧客の配送先住所
    PHONE VARCHAR2(20),                       -- 電話番号：顧客の連絡先電話番号
    CREATED_AT DATE DEFAULT SYSDATE,          -- 作成日時：レコード作成時刻（自動設定）
    UPDATED_AT DATE DEFAULT SYSDATE           -- 更新日時：レコード更新時刻（自動設定）
);

-- ロケーションマスタテーブル：倉庫内の位置情報を管理
-- Location Master Table: Manages warehouse location information
CREATE TABLE LOCATION_MASTER (
    LOCATION_ID VARCHAR2(20) PRIMARY KEY,     -- ロケーションID（主キー）：位置の一意識別子
    WAREHOUSE_CODE VARCHAR2(10) NOT NULL,     -- 倉庫コード（必須）：倉庫の識別コード
    ZONE VARCHAR2(20),                        -- ゾーン：倉庫内のエリア区分（A区、B区等）
    RACK VARCHAR2(20),                        -- ラック：棚の識別番号（R001、R002等）
    LEVEL_NO VARCHAR2(10),                    -- レベル：棚の段数（1段、2段等）
    POSITION VARCHAR2(10),                    -- ポジション：具体的な位置番号（01、02等）
    MAX_CAPACITY NUMBER,                      -- 最大容量：その位置の最大保管可能数量
    CREATED_AT DATE DEFAULT SYSDATE           -- 作成日時：レコード作成時刻（自動設定）
);

-- ===========================================
-- テーブル作成完了
-- Table Creation Completed
-- ===========================================