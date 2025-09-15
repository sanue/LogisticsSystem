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
  Search as SearchIcon,
} from '@mui/icons-material';
import { DataGrid, GridColDef, GridActionsCellItem } from '@mui/x-data-grid';
import { Edit as EditIcon, Delete as DeleteIcon } from '@mui/icons-material';
import { locationApi, Location } from '../services/api';

const LocationManagement: React.FC = () => {
  const [locations, setLocations] = useState<Location[]>([]);
  const [loading, setLoading] = useState(true);
  const [open, setOpen] = useState(false);
  const [editingLocation, setEditingLocation] = useState<Location | null>(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'success' as 'success' | 'error' });

  const [formData, setFormData] = useState({
    locationId: '',
    warehouseCode: '',
    zone: '',
    rack: '',
    levelNo: '',
    position: '',
    maxCapacity: 0,
  });

  useEffect(() => {
    fetchLocations();
  }, []);

  const fetchLocations = async () => {
    try {
      setLoading(true);
      const response = await locationApi.getAll();
      setLocations(response.data);
    } catch (error) {
      console.error('Failed to fetch locations:', error);
      showSnackbar('位置データの取得に失敗しました', 'error');
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async () => {
    if (!searchTerm.trim()) {
      fetchLocations();
      return;
    }

    try {
      setLoading(true);
      const response = await locationApi.search(searchTerm);
      setLocations(response.data);
    } catch (error) {
      console.error('Failed to search locations:', error);
      showSnackbar('位置検索に失敗しました', 'error');
    } finally {
      setLoading(false);
    }
  };

  const handleOpenDialog = (location?: Location) => {
    if (location) {
      setEditingLocation(location);
      setFormData({
        locationId: location.locationId,
        warehouseCode: location.warehouseCode,
        zone: location.zone,
        rack: location.rack,
        levelNo: location.levelNo,
        position: location.position,
        maxCapacity: location.maxCapacity,
      });
    } else {
      setEditingLocation(null);
      setFormData({
        locationId: '',
        warehouseCode: '',
        zone: '',
        rack: '',
        levelNo: '',
        position: '',
        maxCapacity: 0,
      });
    }
    setOpen(true);
  };

  const handleCloseDialog = () => {
    setOpen(false);
    setEditingLocation(null);
    setFormData({
      locationId: '',
      warehouseCode: '',
      zone: '',
      rack: '',
      levelNo: '',
      position: '',
      maxCapacity: 0,
    });
  };

  const handleSubmit = async () => {
    try {
      if (editingLocation) {
        await locationApi.update(editingLocation.locationId, formData);
        showSnackbar('位置を更新しました', 'success');
      } else {
        await locationApi.create(formData);
        showSnackbar('位置を作成しました', 'success');
      }
      handleCloseDialog();
      fetchLocations();
    } catch (error) {
      console.error('Failed to save location:', error);
      showSnackbar('位置の保存に失敗しました', 'error');
    }
  };

  const handleDelete = async (id: string) => {
    if (window.confirm('この位置を削除しますか？')) {
      try {
        await locationApi.delete(id);
        showSnackbar('位置を削除しました', 'success');
        fetchLocations();
      } catch (error) {
        console.error('Failed to delete location:', error);
        showSnackbar('位置の削除に失敗しました', 'error');
      }
    }
  };

  const showSnackbar = (message: string, severity: 'success' | 'error') => {
    setSnackbar({ open: true, message, severity });
  };

  const columns: GridColDef[] = [
    { field: 'locationId', headerName: '位置ID', width: 120 },
    { field: 'warehouseCode', headerName: '倉庫コード', width: 120 },
    { field: 'zone', headerName: 'ゾーン', width: 100 },
    { field: 'rack', headerName: 'ラック', width: 100 },
    { field: 'levelNo', headerName: 'レベル', width: 100 },
    { field: 'position', headerName: '位置', width: 100 },
    {
      field: 'maxCapacity',
      headerName: '最大容量',
      width: 120,
      renderCell: (params) => (
        <Chip
          label={params.value}
          color={params.value > 50 ? 'success' : 'default'}
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
          onClick={() => handleDelete(params.row.locationId)}
        />,
      ],
    },
  ];

  return (
    <Box>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4">位置管理</Typography>
        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={() => handleOpenDialog()}
        >
          新規位置
        </Button>
      </Box>

      <Paper sx={{ mb: 3, p: 2 }}>
        <Grid container spacing={2} alignItems="center">
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label="倉庫コードで検索"
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
                fetchLocations();
              }}
            >
              リセット
            </Button>
          </Grid>
        </Grid>
      </Paper>

      <Paper sx={{ height: 600, width: '100%' }}>
        <DataGrid
          rows={locations}
          columns={columns}
          loading={loading}
          pageSizeOptions={[5, 10, 25, 50]}
          initialState={{
            pagination: { paginationModel: { pageSize: 10 } },
          }}
          pagination
          paginationMode="client"
          disableRowSelectionOnClick
          getRowId={(row) => row.locationId}
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
          {editingLocation ? '位置編集' : '新規位置'}
        </DialogTitle>
        <DialogContent>
          <Grid container spacing={2} sx={{ mt: 1 }}>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="位置ID"
                value={formData.locationId}
                onChange={(e) => setFormData({ ...formData, locationId: e.target.value })}
                disabled={!!editingLocation}
                required
              />
            </Grid>
            <Grid item xs={6}>
              <TextField
                fullWidth
                label="倉庫コード"
                value={formData.warehouseCode}
                onChange={(e) => setFormData({ ...formData, warehouseCode: e.target.value })}
                required
              />
            </Grid>
            <Grid item xs={6}>
              <TextField
                fullWidth
                label="ゾーン"
                value={formData.zone}
                onChange={(e) => setFormData({ ...formData, zone: e.target.value })}
                required
              />
            </Grid>
            <Grid item xs={6}>
              <TextField
                fullWidth
                label="ラック"
                value={formData.rack}
                onChange={(e) => setFormData({ ...formData, rack: e.target.value })}
                required
              />
            </Grid>
            <Grid item xs={6}>
              <TextField
                fullWidth
                label="レベル"
                value={formData.levelNo}
                onChange={(e) => setFormData({ ...formData, levelNo: e.target.value })}
                required
              />
            </Grid>
            <Grid item xs={6}>
              <TextField
                fullWidth
                label="位置"
                value={formData.position}
                onChange={(e) => setFormData({ ...formData, position: e.target.value })}
                required
              />
            </Grid>
            <Grid item xs={6}>
              <TextField
                fullWidth
                label="最大容量"
                type="number"
                value={formData.maxCapacity}
                onChange={(e) => setFormData({ ...formData, maxCapacity: parseInt(e.target.value) || 0 })}
                required
              />
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDialog}>キャンセル</Button>
          <Button onClick={handleSubmit} variant="contained">
            {editingLocation ? '更新' : '作成'}
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

export default LocationManagement;
