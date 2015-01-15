namespace ASTIC_client
{
    partial class QueryForm
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
            this.pn_query = new System.Windows.Forms.Panel();
            this.lbl_query = new System.Windows.Forms.Label();
            this.btn_search = new System.Windows.Forms.Button();
            this.sc_results = new System.Windows.Forms.SplitContainer();
            this.lb_reslts = new System.Windows.Forms.ListBox();
            this.treeView1 = new System.Windows.Forms.TreeView();
            this.statusStrip1 = new System.Windows.Forms.StatusStrip();
            this.toolStripStatusLabel1 = new System.Windows.Forms.ToolStripStatusLabel();
            this.toolStripProgressBar1 = new System.Windows.Forms.ToolStripProgressBar();
            this.cb_query = new System.Windows.Forms.ComboBox();
            this.pn_query.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.sc_results)).BeginInit();
            this.sc_results.Panel1.SuspendLayout();
            this.sc_results.Panel2.SuspendLayout();
            this.sc_results.SuspendLayout();
            this.statusStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // pn_query
            // 
            this.pn_query.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.pn_query.Controls.Add(this.cb_query);
            this.pn_query.Controls.Add(this.btn_search);
            this.pn_query.Controls.Add(this.lbl_query);
            this.pn_query.ForeColor = System.Drawing.SystemColors.ControlText;
            this.pn_query.Location = new System.Drawing.Point(2, 2);
            this.pn_query.Name = "pn_query";
            this.pn_query.Size = new System.Drawing.Size(515, 50);
            this.pn_query.TabIndex = 0;
            // 
            // lbl_query
            // 
            this.lbl_query.AutoSize = true;
            this.lbl_query.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.lbl_query.Location = new System.Drawing.Point(11, 15);
            this.lbl_query.Name = "lbl_query";
            this.lbl_query.Size = new System.Drawing.Size(56, 20);
            this.lbl_query.TabIndex = 0;
            this.lbl_query.Text = "Cauta:";
            // 
            // btn_search
            // 
            this.btn_search.Location = new System.Drawing.Point(421, 12);
            this.btn_search.Name = "btn_search";
            this.btn_search.Size = new System.Drawing.Size(87, 23);
            this.btn_search.TabIndex = 2;
            this.btn_search.Text = "Cauta";
            this.btn_search.UseVisualStyleBackColor = true;
            this.btn_search.MouseClick += new System.Windows.Forms.MouseEventHandler(this.btn_search_MouseClick);
            // 
            // sc_results
            // 
            this.sc_results.Location = new System.Drawing.Point(2, 55);
            this.sc_results.Name = "sc_results";
            // 
            // sc_results.Panel1
            // 
            this.sc_results.Panel1.Controls.Add(this.treeView1);
            // 
            // sc_results.Panel2
            // 
            this.sc_results.Panel2.Controls.Add(this.lb_reslts);
            this.sc_results.Size = new System.Drawing.Size(515, 206);
            this.sc_results.SplitterDistance = 134;
            this.sc_results.TabIndex = 1;
            // 
            // lb_reslts
            // 
            this.lb_reslts.ForeColor = System.Drawing.SystemColors.WindowText;
            this.lb_reslts.FormattingEnabled = true;
            this.lb_reslts.Items.AddRange(new object[] {
            "File 1",
            "File 2"});
            this.lb_reslts.Location = new System.Drawing.Point(3, 3);
            this.lb_reslts.Name = "lb_reslts";
            this.lb_reslts.Size = new System.Drawing.Size(371, 199);
            this.lb_reslts.TabIndex = 0;
            // 
            // treeView1
            // 
            this.treeView1.Location = new System.Drawing.Point(3, 3);
            this.treeView1.Name = "treeView1";
            this.treeView1.Size = new System.Drawing.Size(128, 199);
            this.treeView1.TabIndex = 0;
            // 
            // statusStrip1
            // 
            this.statusStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolStripStatusLabel1,
            this.toolStripProgressBar1});
            this.statusStrip1.Location = new System.Drawing.Point(0, 263);
            this.statusStrip1.Name = "statusStrip1";
            this.statusStrip1.Size = new System.Drawing.Size(518, 22);
            this.statusStrip1.TabIndex = 2;
            this.statusStrip1.Text = "statusStrip1";
            // 
            // toolStripStatusLabel1
            // 
            this.toolStripStatusLabel1.Name = "toolStripStatusLabel1";
            this.toolStripStatusLabel1.Size = new System.Drawing.Size(97, 17);
            this.toolStripStatusLabel1.Text = "Incepeti sa scrieti";
            // 
            // toolStripProgressBar1
            // 
            this.toolStripProgressBar1.Name = "toolStripProgressBar1";
            this.toolStripProgressBar1.Size = new System.Drawing.Size(100, 16);
            // 
            // cb_query
            // 
            this.cb_query.FormattingEnabled = true;
            this.cb_query.Location = new System.Drawing.Point(74, 13);
            this.cb_query.Name = "cb_query";
            this.cb_query.Size = new System.Drawing.Size(341, 21);
            this.cb_query.TabIndex = 3;
            this.cb_query.TextChanged += new System.EventHandler(this.cb_query_TextChanged);
            // 
            // QueryForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(518, 285);
            this.Controls.Add(this.statusStrip1);
            this.Controls.Add(this.sc_results);
            this.Controls.Add(this.pn_query);
            this.Name = "QueryForm";
            this.Text = "ASTIC";
            this.Load += new System.EventHandler(this.QueryForm_Load);
            this.pn_query.ResumeLayout(false);
            this.pn_query.PerformLayout();
            this.sc_results.Panel1.ResumeLayout(false);
            this.sc_results.Panel2.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.sc_results)).EndInit();
            this.sc_results.ResumeLayout(false);
            this.statusStrip1.ResumeLayout(false);
            this.statusStrip1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Panel pn_query;
        private System.Windows.Forms.Button btn_search;
        private System.Windows.Forms.Label lbl_query;
        private System.Windows.Forms.SplitContainer sc_results;
        private System.Windows.Forms.TreeView treeView1;
        private System.Windows.Forms.ListBox lb_reslts;
        private System.Windows.Forms.StatusStrip statusStrip1;
        private System.Windows.Forms.ToolStripStatusLabel toolStripStatusLabel1;
        private System.Windows.Forms.ToolStripProgressBar toolStripProgressBar1;
        private System.Windows.Forms.ComboBox cb_query;
    }
}

