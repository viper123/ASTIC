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
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.btn_search = new System.Windows.Forms.Button();
            this.lbl_query = new System.Windows.Forms.Label();
            this.sc_results = new System.Windows.Forms.SplitContainer();
            this.treeView1 = new System.Windows.Forms.TreeView();
            this.lb_results = new System.Windows.Forms.ListBox();
            this.statusStrip1 = new System.Windows.Forms.StatusStrip();
            this.toolStripStatusLabel1 = new System.Windows.Forms.ToolStripStatusLabel();
            this.toolStripProgressBar1 = new System.Windows.Forms.ToolStripProgressBar();
            this.lb_predictions = new System.Windows.Forms.ListBox();
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
            this.pn_query.Controls.Add(this.lb_predictions);
            this.pn_query.Controls.Add(this.sc_results);
            this.pn_query.Controls.Add(this.textBox1);
            this.pn_query.Controls.Add(this.btn_search);
            this.pn_query.Controls.Add(this.lbl_query);
            this.pn_query.ForeColor = System.Drawing.SystemColors.ControlText;
            this.pn_query.Location = new System.Drawing.Point(3, 2);
            this.pn_query.Margin = new System.Windows.Forms.Padding(4);
            this.pn_query.Name = "pn_query";
            this.pn_query.Size = new System.Drawing.Size(999, 441);
            this.pn_query.TabIndex = 0;
            // 
            // textBox1
            // 
            this.textBox1.AutoCompleteMode = System.Windows.Forms.AutoCompleteMode.Suggest;
            this.textBox1.AutoCompleteSource = System.Windows.Forms.AutoCompleteSource.CustomSource;
            this.textBox1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.textBox1.Location = new System.Drawing.Point(84, 19);
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(311, 27);
            this.textBox1.TabIndex = 4;
            this.textBox1.TextChanged += new System.EventHandler(this.textBox1_TextChanged);
            this.textBox1.KeyUp += new System.Windows.Forms.KeyEventHandler(this.textBox1_KeyUp);
            // 
            // btn_search
            // 
            this.btn_search.Location = new System.Drawing.Point(417, 18);
            this.btn_search.Margin = new System.Windows.Forms.Padding(4);
            this.btn_search.Name = "btn_search";
            this.btn_search.Size = new System.Drawing.Size(116, 28);
            this.btn_search.TabIndex = 2;
            this.btn_search.Text = "Cauta";
            this.btn_search.UseVisualStyleBackColor = true;
            this.btn_search.MouseClick += new System.Windows.Forms.MouseEventHandler(this.btn_search_MouseClick);
            // 
            // lbl_query
            // 
            this.lbl_query.AutoSize = true;
            this.lbl_query.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.lbl_query.Location = new System.Drawing.Point(15, 18);
            this.lbl_query.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.lbl_query.Name = "lbl_query";
            this.lbl_query.Size = new System.Drawing.Size(71, 25);
            this.lbl_query.TabIndex = 0;
            this.lbl_query.Text = "Cauta:";
            // 
            // sc_results
            // 
            this.sc_results.Location = new System.Drawing.Point(9, 54);
            this.sc_results.Margin = new System.Windows.Forms.Padding(4);
            this.sc_results.Name = "sc_results";
            // 
            // sc_results.Panel1
            // 
            this.sc_results.Panel1.Controls.Add(this.treeView1);
            // 
            // sc_results.Panel2
            // 
            this.sc_results.Panel2.Controls.Add(this.lb_results);
            this.sc_results.Size = new System.Drawing.Size(976, 381);
            this.sc_results.SplitterDistance = 404;
            this.sc_results.SplitterWidth = 5;
            this.sc_results.TabIndex = 1;
            // 
            // treeView1
            // 
            this.treeView1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.treeView1.Location = new System.Drawing.Point(0, 0);
            this.treeView1.Margin = new System.Windows.Forms.Padding(4);
            this.treeView1.Name = "treeView1";
            this.treeView1.Size = new System.Drawing.Size(404, 381);
            this.treeView1.TabIndex = 0;
            this.treeView1.NodeMouseClick += new System.Windows.Forms.TreeNodeMouseClickEventHandler(this.treeView1_NodeMouseClick);
            // 
            // lb_results
            // 
            this.lb_results.ForeColor = System.Drawing.SystemColors.WindowText;
            this.lb_results.FormattingEnabled = true;
            this.lb_results.ItemHeight = 16;
            this.lb_results.Location = new System.Drawing.Point(4, 4);
            this.lb_results.Margin = new System.Windows.Forms.Padding(4);
            this.lb_results.Name = "lb_results";
            this.lb_results.Size = new System.Drawing.Size(568, 484);
            this.lb_results.TabIndex = 0;
            // 
            // statusStrip1
            // 
            this.statusStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolStripStatusLabel1,
            this.toolStripProgressBar1});
            this.statusStrip1.Location = new System.Drawing.Point(0, 563);
            this.statusStrip1.Name = "statusStrip1";
            this.statusStrip1.Padding = new System.Windows.Forms.Padding(1, 0, 19, 0);
            this.statusStrip1.Size = new System.Drawing.Size(1002, 26);
            this.statusStrip1.TabIndex = 2;
            this.statusStrip1.Text = "statusStrip1";
            // 
            // toolStripStatusLabel1
            // 
            this.toolStripStatusLabel1.Name = "toolStripStatusLabel1";
            this.toolStripStatusLabel1.Size = new System.Drawing.Size(123, 21);
            this.toolStripStatusLabel1.Text = "Incepeti sa scrieti";
            // 
            // toolStripProgressBar1
            // 
            this.toolStripProgressBar1.Name = "toolStripProgressBar1";
            this.toolStripProgressBar1.Size = new System.Drawing.Size(133, 20);
            // 
            // lb_predictions
            // 
            this.lb_predictions.FormattingEnabled = true;
            this.lb_predictions.ItemHeight = 16;
            this.lb_predictions.Location = new System.Drawing.Point(84, 46);
            this.lb_predictions.Name = "lb_predictions";
            this.lb_predictions.Size = new System.Drawing.Size(311, 148);
            this.lb_predictions.TabIndex = 5;
            this.lb_predictions.Visible = false;
            this.lb_predictions.SelectedIndexChanged += new System.EventHandler(this.lb_predictions_SelectedIndexChanged);
            // 
            // QueryForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1002, 589);
            this.Controls.Add(this.statusStrip1);
            this.Controls.Add(this.pn_query);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.Margin = new System.Windows.Forms.Padding(4);
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
        private System.Windows.Forms.ListBox lb_results;
        private System.Windows.Forms.StatusStrip statusStrip1;
        private System.Windows.Forms.ToolStripStatusLabel toolStripStatusLabel1;
        private System.Windows.Forms.ToolStripProgressBar toolStripProgressBar1;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.ListBox lb_predictions;
    }
}

