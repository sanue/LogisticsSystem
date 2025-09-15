import React, { useState, useEffect } from 'react';
import {
  Box,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Grid,
  Paper,
  TextField,
  Typography,
  Alert,
  Snackbar,
  Chip,
} from '@mui/material';
import {
  Add as AddIcon,
  Edit as EditIcon,
  Delete as DeleteIcon,
  Search as SearchIcon,
} from '@mui/icons-material';
import { DataGrid, GridColDef, GridActionsCellItem } from '@mui/x-data-grid';
import { productApi, Product, PageResponse } from '../services/api';

const ProductManagement: React.FC = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(true);
  const [open, setOpen] = useState(false);
  const [editingProduct, setEditingProduct] = useState<Product | null>(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'success' as 'success' | 'error' });
  
  // 分页状态
  const [pagination, setPagination] = useState({
    page: 0,
    pageSize: 10,
    total: 0,
    totalPages: 0
  });

  const [formData, setFormData] = useState({
    productId: '',
    productName: '',
    specification: '',
    unit: '',
    safetyStock: 0,
  });

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async (page: number = pagination.page, pageSize: number = pagination.pageSize, keyword?: string) => {
    try {
      setLoading(true);
      const response = await productApi.getWithPagination(page, pageSize, 'createdAt', 'DESC', keyword);
      const pageData = response.data;
      setProducts(pageData.content);
      setPagination({
        page: pageData.page,
        pageSize: pageData.size,
        total: pageData.totalElements,
        totalPages: pageData.totalPages
      });
    } catch (error) {
      console.error('Failed to fetch products:', error);
      showSnackbar('商品データの取得に失敗しました', 'error');
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async () => {
    fetchProducts(0, pagination.pageSize, searchTerm.trim() || undefined);
  };

  const handleOpenDialog = (product?: Product) => {
    if (product) {
      setEditingProduct(product);
      setFormData({
        productId: product.productId,
        productName: product.productName,
        specification: product.specification || '',
        unit: product.unit,
        safetyStock: product.safetyStock,
      });
    } else {
      setEditingProduct(null);
      setFormData({
        productId: '',
        productName: '',
        specification: '',
        unit: '',
        safetyStock: 0,
      });
    }
    setOpen(true);
  };

  const handleCloseDialog = () => {
    setOpen(false);
    setEditingProduct(null);
    setFormData({
      productId: '',
      productName: '',
      specification: '',
      unit: '',
      safetyStock: 0,
    });
  };

  const handleSubmit = async () => {
    try {
      if (editingProduct) {
        await productApi.update(editingProduct.productId, formData);
        showSnackbar('商品を更新しました', 'success');
      } else {
        await productApi.create(formData);
        showSnackbar('商品を作成しました', 'success');
      }
      handleCloseDialog();
      fetchProducts(pagination.page, pagination.pageSize, searchTerm.trim() || undefined);
    } catch (error) {
      console.error('Failed to save product:', error);
      showSnackbar('商品の保存に失敗しました', 'error');
    }
  };

  const handleDelete = async (id: string) => {
    if (window.confirm('この商品を削除しますか？')) {
      try {
        await productApi.delete(id);
        showSnackbar('商品を削除しました', 'success');
        fetchProducts(pagination.page, pagination.pageSize, searchTerm.trim() || undefined);
      } catch (error) {
        console.error('Failed to delete product:', error);
        showSnackbar('商品の削除に失敗しました', 'error');
      }
    }
  };

  const showSnackbar = (message: string, severity: 'success' | 'error') => {
    setSnackbar({ open: true, message, severity });
  };

  const columns: GridColDef[] = [
    { field: 'productId', headerName: '商品ID', width: 120 },
    { field: 'productName', headerName: '商品名', width: 200 },
    { field: 'specification', headerName: '規格', width: 200 },
    { field: 'unit', headerName: '単位', width: 100 },
    {
      field: 'safetyStock',
      headerName: '安全在庫',
      width: 120,
      renderCell: (params) => (
        <Chip
          label={params.value}
          color={params.value > 10 ? 'success' : 'warning'}
          size="small"
        />
      ),
    },
    {
      field: 'createdAt',
      headerName: '作成日時',
      width: 180,
      valueFormatter: (params: any) => new Date(params.value).toLocaleString('ja-JP'),
    },
    {
      field: 'actions',
      type: 'actions',
      headerName: '操作',
      width: 120,
      getActions: (params) => [
        <GridActionsCellItem
          icon={<EditIcon />}
          label="編集"
          onClick={() => handleOpenDialog(params.row)}
        />,
        <GridActionsCellItem
          icon={<DeleteIcon />}
          label="削除"
          onClick={() => handleDelete(params.row.productId)}
        />,
      ],
    },
  ];

  return (
    <Box>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4">商品管理</Typography>
        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={() => handleOpenDialog()}
        >
          新規商品
        </Button>
      </Box>

      <Paper sx={{ mb: 3, p: 2 }}>
        <Grid container spacing={2} alignItems="center">
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label="商品名で検索"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
            />
          </Grid>
          <Grid item xs={12} sm={3}>
            <Button
              fullWidth
              variant="outlined"
              startIcon={<SearchIcon />}
              onClick={handleSearch}
            >
              検索
            </Button>
          </Grid>
          <Grid item xs={12} sm={3}>
            <Button
              fullWidth
              variant="outlined"
              onClick={() => {
                setSearchTerm('');
                fetchProducts();
              }}
            >
              リセット
            </Button>
          </Grid>
        </Grid>
      </Paper>

      <Paper sx={{ height: 600, width: '100%' }}>
        <DataGrid
          rows={products}
          columns={columns}
          loading={loading}
          pageSizeOptions={[5, 10, 25, 50]}
          paginationModel={{
            page: pagination.page,
            pageSize: pagination.pageSize,
          }}
          rowCount={pagination.total}
          paginationMode="server"
          onPaginationModelChange={(model) => {
            fetchProducts(model.page, model.pageSize, searchTerm.trim() || undefined);
          }}
          disableRowSelectionOnClick
          getRowId={(row) => row.productId}
          sx={{
            '& .MuiDataGrid-footerContainer': {
              display: 'flex',
              justifyContent: 'space-between',
              alignItems: 'center',
            },
          }}
        />
      </Paper>

      <Dialog open={open} onClose={handleCloseDialog} maxWidth="sm" fullWidth>
        <DialogTitle>
          {editingProduct ? '商品編集' : '新規商品'}
        </DialogTitle>
        <DialogContent>
          <Grid container spacing={2} sx={{ mt: 1 }}>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="商品ID"
                value={formData.productId}
                onChange={(e) => setFormData({ ...formData, productId: e.target.value })}
                disabled={!!editingProduct}
                required
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="商品名"
                value={formData.productName}
                onChange={(e) => setFormData({ ...formData, productName: e.target.value })}
                required
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="規格"
                value={formData.specification}
                onChange={(e) => setFormData({ ...formData, specification: e.target.value })}
              />
            </Grid>
            <Grid item xs={6}>
              <TextField
                fullWidth
                label="単位"
                value={formData.unit}
                onChange={(e) => setFormData({ ...formData, unit: e.target.value })}
                required
              />
            </Grid>
            <Grid item xs={6}>
              <TextField
                fullWidth
                label="安全在庫"
                type="number"
                value={formData.safetyStock}
                onChange={(e) => setFormData({ ...formData, safetyStock: parseInt(e.target.value) || 0 })}
                required
              />
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDialog}>キャンセル</Button>
          <Button onClick={handleSubmit} variant="contained">
            {editingProduct ? '更新' : '作成'}
          </Button>
        </DialogActions>
      </Dialog>

      <Snackbar
        open={snackbar.open}
        autoHideDuration={6000}
        onClose={() => setSnackbar({ ...snackbar, open: false })}
      >
        <Alert
          onClose={() => setSnackbar({ ...snackbar, open: false })}
          severity={snackbar.severity}
        >
          {snackbar.message}
        </Alert>
      </Snackbar>
    </Box>
  );
};

export default ProductManagement;
