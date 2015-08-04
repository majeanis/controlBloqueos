namespace clienteRecob
{
    partial class formPrincipal
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
            this.gridCajas = new System.Windows.Forms.DataGridView();
            this.dataCajas = new System.Data.DataSet();
            this.tablCajas = new System.Data.DataTable();
            this.dataColumn1 = new System.Data.DataColumn();
            this.dataColumn2 = new System.Data.DataColumn();
            this.id = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.numero = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.nombre = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.vigente = new System.Windows.Forms.DataGridViewCheckBoxColumn();
            ((System.ComponentModel.ISupportInitialize)(this.gridCajas)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataCajas)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.tablCajas)).BeginInit();
            this.SuspendLayout();
            // 
            // gridCajas
            // 
            this.gridCajas.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.gridCajas.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.id,
            this.numero,
            this.nombre,
            this.vigente});
            this.gridCajas.Dock = System.Windows.Forms.DockStyle.Fill;
            this.gridCajas.Location = new System.Drawing.Point(0, 0);
            this.gridCajas.Margin = new System.Windows.Forms.Padding(6, 6, 6, 6);
            this.gridCajas.MultiSelect = false;
            this.gridCajas.Name = "gridCajas";
            this.gridCajas.RowHeadersVisible = false;
            this.gridCajas.RowTemplate.Height = 32;
            this.gridCajas.Size = new System.Drawing.Size(1370, 667);
            this.gridCajas.TabIndex = 0;
            // 
            // dataCajas
            // 
            this.dataCajas.CaseSensitive = true;
            this.dataCajas.DataSetName = "setCajas";
            this.dataCajas.RemotingFormat = System.Data.SerializationFormat.Binary;
            this.dataCajas.Tables.AddRange(new System.Data.DataTable[] {
            this.tablCajas});
            // 
            // tablCajas
            // 
            this.tablCajas.Columns.AddRange(new System.Data.DataColumn[] {
            this.dataColumn1,
            this.dataColumn2});
            this.tablCajas.RemotingFormat = System.Data.SerializationFormat.Binary;
            this.tablCajas.TableName = "cajas";
            // 
            // dataColumn1
            // 
            this.dataColumn1.ColumnName = "id";
            // 
            // dataColumn2
            // 
            this.dataColumn2.ColumnName = "numero";
            // 
            // id
            // 
            this.id.HeaderText = "id";
            this.id.Name = "id";
            // 
            // numero
            // 
            this.numero.HeaderText = "numero";
            this.numero.Name = "numero";
            // 
            // nombre
            // 
            this.nombre.HeaderText = "nombre";
            this.nombre.Name = "nombre";
            // 
            // vigente
            // 
            this.vigente.HeaderText = "vigente";
            this.vigente.Name = "vigente";
            // 
            // formPrincipal
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(11F, 24F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1370, 667);
            this.Controls.Add(this.gridCajas);
            this.Font = new System.Drawing.Font("Microsoft Sans Serif", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Margin = new System.Windows.Forms.Padding(6, 6, 6, 6);
            this.Name = "formPrincipal";
            this.Text = "Registro y Control de Bloqueos";
            this.Load += new System.EventHandler(this.formPrincipal_Load);
            ((System.ComponentModel.ISupportInitialize)(this.gridCajas)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataCajas)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.tablCajas)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView gridCajas;
        private System.Data.DataSet dataCajas;
        private System.Data.DataTable tablCajas;
        private System.Data.DataColumn dataColumn1;
        private System.Data.DataColumn dataColumn2;
        private System.Windows.Forms.DataGridViewTextBoxColumn id;
        private System.Windows.Forms.DataGridViewTextBoxColumn numero;
        private System.Windows.Forms.DataGridViewTextBoxColumn nombre;
        private System.Windows.Forms.DataGridViewCheckBoxColumn vigente;
    }
}