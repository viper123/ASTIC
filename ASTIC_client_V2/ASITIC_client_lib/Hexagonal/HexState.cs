using System;
using System.Collections.Generic;
using System.Text;
using System.Drawing;

namespace Hexagonal
{
	public class HexState
	{
		private System.Drawing.Color backgroundColor;
        private System.Drawing.Color borderColor;
		

		public System.Drawing.Color BackgroundColor
		{
			get
			{
				return backgroundColor;
			}
			set
			{
				backgroundColor = value;
			}
		}

        public System.Drawing.Color BorderColor
        {
            get
            {
                return borderColor;
            }
            set
            {
                borderColor = value;
            }
        }


		public HexState()
		{
			this.backgroundColor = Color.White;
            this.borderColor = Color.LightGray;
		}

        public HexState(int size)
        {
            this.borderColor = Color.LightGray;
            switch (size)
            {
                case 1:
                    this.backgroundColor = (Color)new ColorConverter().ConvertFromString("#FFF3E0");
                    break;
                case 2:
                    this.backgroundColor = (Color)new ColorConverter().ConvertFromString("#FFE0B2");
                    break;
                case 3:
                    this.backgroundColor = (Color)new ColorConverter().ConvertFromString("#FFCC80");
                    break;
                case 4:
                    this.backgroundColor = (Color)new ColorConverter().ConvertFromString("#FFB74D");
                    break;
                case 5:
                    this.backgroundColor = (Color)new ColorConverter().ConvertFromString("#FFA726");
                    break;
                case 6:
                    this.backgroundColor = (Color)new ColorConverter().ConvertFromString("#FF9800");
                    break;
                case 7:
                    this.backgroundColor = (Color)new ColorConverter().ConvertFromString("#FB8C00");
                    break;
                case 8:
                    this.backgroundColor = (Color)new ColorConverter().ConvertFromString("#F57C00");
                    break;
                case 9:
                    this.backgroundColor = (Color)new ColorConverter().ConvertFromString("#EF6C00");
                    break;
                case 10:
                    this.backgroundColor = (Color)new ColorConverter().ConvertFromString("#E65100");
                    break;
                default:
                    this.backgroundColor = (Color)new ColorConverter().ConvertFromString("#E65100");
                    break;
            }
        }

	}
}
