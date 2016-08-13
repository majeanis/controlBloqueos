namespace Recob.Cliente
{
    partial class FrmConfCajasBloqueo
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle1 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle2 = new System.Windows.Forms.DataGridViewCellStyle();
            this.BtnGuardar = new System.Windows.Forms.Button();
            this.BtnEliminar = new System.Windows.Forms.Button();
            this.BtnCerrar = new System.Windows.Forms.Button();
            this.GrdDetalle = new System.Windows.Forms.DataGridView();
            this.estado = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.numero = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.nombre = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.vigente = new System.Windows.Forms.DataGridViewCheckBoxColumn();
            this.TxtCaja = new System.Windows.Forms.TextBox();
            ((System.ComponentModel.ISupportInitialize)(this.GrdDetalle)).BeginInit();
            this.SuspendLayout();
            // 
            // BtnGuardar
            // 
            this.BtnGuardar.Location = new System.Drawing.Point(616, 8);
            this.BtnGuardar.Margin = new System.Windows.Forms.Padding(4);
            this.BtnGuardar.Name = "BtnGuardar";
            this.BtnGuardar.Size = new System.Drawing.Size(139, 56);
            this.BtnGuardar.TabIndex = 1;
            this.BtnGuardar.Text = "Guardar";
            this.BtnGuardar.UseVisualStyleBackColor = true;
            this.BtnGuardar.Click += new System.EventHandler(this.BtnGuardar_Click);
            // 
            // BtnEliminar
            // 
            this.BtnEliminar.Location = new System.Drawing.Point(616, 72);
            this.BtnEliminar.Margin = new System.Windows.Forms.Padding(4);
            this.BtnEliminar.Name = "BtnEliminar";
            this.BtnEliminar.Size = new System.Drawing.Size(139, 56);
            this.BtnEliminar.TabIndex = 2;
            this.BtnEliminar.Text = "Eliminar";
            this.BtnEliminar.UseVisualStyleBackColor = true;
            this.BtnEliminar.Click += new System.EventHandler(this.BtnEliminar_Click);
            // 
            // BtnCerrar
            // 
            this.BtnCerrar.Location = new System.Drawing.Point(616, 240);
            this.BtnCerrar.Margin = new System.Windows.Forms.Padding(4);
            this.BtnCerrar.Name = "BtnCerrar";
            this.BtnCerrar.Size = new System.Drawing.Size(139, 56);
            this.BtnCerrar.TabIndex = 3;
            this.BtnCerrar.Text = "Cerrar";
            this.BtnCerrar.UseVisualStyleBackColor = true;
            // 
            // GrdDetalle
            // 
            this.GrdDetalle.AllowUserToResizeColumns = false;
            this.GrdDetalle.AllowUserToResizeRows = false;
            this.GrdDetalle.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.GrdDetalle.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.estado,
            this.numero,
            this.nombre,
            this.vigente});
            this.GrdDetalle.EditMode = System.Windows.Forms.DataGridViewEditMode.EditOnKeystroke;
            this.GrdDetalle.Location = new System.Drawing.Point(8, 8);
            this.GrdDetalle.MultiSelect = false;
            this.GrdDetalle.Name = "GrdDetalle";
            this.GrdDetalle.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.GrdDetalle.Size = new System.Drawing.Size(600, 288);
            this.GrdDetalle.TabIndex = 5;
            this.GrdDetalle.CellValueChanged += new System.Windows.Forms.DataGridViewCellEventHandler(this.GrdDetalle_CellValueChanged);
            this.GrdDetalle.UserAddedRow += new System.Windows.Forms.DataGridViewRowEventHandler(this.GrdDetalle_UserAddedRow);
            this.GrdDetalle.UserDeletingRow += new System.Windows.Forms.DataGridViewRowCancelEventHandler(this.GrdDetalle_UserDeletingRow);
            // 
            // estado
            // 
            this.estado.Frozen = true;
            this.estado.HeaderText = "Estado";
            this.estado.Name = "estado";
            this.estado.ReadOnly = true;
            this.estado.Width = 90;
            // 
            // numero
            // 
            dataGridViewCellStyle1.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleRight;
            this.numero.DefaultCellStyle = dataGridViewCellStyle1;
            this.numero.Frozen = true;
            this.numero.HeaderText = "Número";
            this.numero.MaxInputLength = 9999;
            this.numero.Name = "numero";
            this.numero.Resizable = System.Windows.Forms.DataGridViewTriState.False;
            this.numero.Width = 80;
            // 
            // nombre
            // 
            this.nombre.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.Fill;
            dataGridViewCellStyle2.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            this.nombre.DefaultCellStyle = dataGridViewCellStyle2;
            this.nombre.HeaderText = "Nombre";
            this.nombre.Name = "nombre";
            // 
            // vigente
            // 
            this.vigente.HeaderText = "Vigente";
            this.vigente.Name = "vigente";
            this.vigente.Resizable = System.Windows.Forms.DataGridViewTriState.False;
            this.vigente.Width = 60;
            // 
            // TxtCaja
            // 
            this.TxtCaja.Location = new System.Drawing.Point(8, 304);
            this.TxtCaja.Name = "TxtCaja";
            this.TxtCaja.Size = new System.Drawing.Size(728, 22);
            this.TxtCaja.TabIndex = 6;
            // 
            // FrmConfCajasBloqueo
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(763, 368);
            this.Controls.Add(this.TxtCaja);
            this.Controls.Add(this.GrdDetalle);
            this.Controls.Add(this.BtnCerrar);
            this.Controls.Add(this.BtnEliminar);
            this.Controls.Add(this.BtnGuardar);
            this.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.Margin = new System.Windows.Forms.Padding(4);
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "FrmConfCajasBloqueo";
            this.ShowInTaskbar = false;
            this.Text = "Configuración: Cajas de Bloqueo";
            this.Load += new System.EventHandler(this.FrmConfCajasBloqueo_Load);
            ((System.ComponentModel.ISupportInitialize)(this.GrdDetalle)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button BtnGuardar;
        private System.Windows.Forms.Button BtnEliminar;
        private System.Windows.Forms.Button BtnCerrar;
        private System.Windows.Forms.DataGridView GrdDetalle;
        private System.Windows.Forms.DataGridViewTextBoxColumn numeroDataGridViewTextBoxColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn nombreDataGridViewTextBoxColumn;
        private System.Windows.Forms.DataGridViewCheckBoxColumn vigenteDataGridViewCheckBoxColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn ubicacionDataGridViewTextBoxColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn estado;
        private System.Windows.Forms.DataGridViewTextBoxColumn numero;
        private System.Windows.Forms.DataGridViewTextBoxColumn nombre;
        private System.Windows.Forms.DataGridViewCheckBoxColumn vigente;
        private System.Windows.Forms.TextBox TxtCaja;
    }
}