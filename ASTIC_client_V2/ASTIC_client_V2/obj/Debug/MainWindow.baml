   M S B A M L   `   `   `  ����O  Kmscorlib, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e0891&clr-namespace:System;assembly=mscorlibSystem  5   	   J FASTIC_client_V2, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null1clr-namespace:ASTIC_client_V2ASTIC_client_V2 5   	   Z VGraphX.WPF.Controls, Version=2.1.8.0, Culture=neutral, PublicKeyToken=bab23663f80c9e76N:clr-namespace:GraphX.Controls;assembly=GraphX.WPF.ControlsGraphX.Controls 5   	       ASTIC_client_V2.MainWindow   5   	   R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   9System&clr-namespace:System;assembly=mscorlib     5   	   /localclr-namespace:ASTIC_client_V2     5   	   Q
graphxctrl:clr-namespace:GraphX.Controls;assembly=GraphX.WPF.Controls     5   	   -   5          � Title$  ASTIC��5   	   $��473.2��6   $	��807��6&    � 
ResizeMode$ NoResize=�62   .��5      � .+�5	   
   �� $��Interogare:��6   $
��Left=�6&   $��	10,10,0,0q�6A   $	��Top=�6T   $��98.153��6l   $��16(�6{   $
��Bold"�6�   �� 5
   
   -   5         ��Name$ textBox1��5
      $
��Left=�6$   $��27��6?   $��
113,14,0,0q�6K    �� TextWrapping$
 Wrap=�6_   $�� ��6s   $	��Top=�6{   $	��352��6�   $��16(�6�   6�   �� 5   
   -   5        $��Cauta��5      $
��Left=�6!   $��
474,10,0,0q�6<   $	��Top=�6P   $��90��6h   $��34��6s   $��16(�6   $
��Bold"�6�   6�   �� 5   
   -   5         ��Name$ cb_types��5      $
��Left=�6%   $��
577,10,0,0q�6@   $	��Top=�6T   $��82��6l   $��34��6w   $��16(�5      $
��Bold"�6!   63   �� 5   
   -   5         ��Name$ scroll��5      $��0,55,9.8,37.6q�6(   $
��Auto=�6?   .��5      # ASTIC_client_V2.SpecialCanvas  -   5          Name$ canvas��5   "   #  ASTIC_client_V2.SpecialCanvas   	MouseDown$( "clusterPreview_MouseLeftButtonDown��6V   
�������$��0,0,0,0q�6E   5      5      R� 5   
   -   5         R�Name$ 	treeView1��5      $
��Left=�6&   $��353.2��6A   $��0,55,0,0q�6P   $	��Top=�5      $��309.6��6+   69   �� 5   
   -   5        	 ��Name$	 clusterPreview��5      $	��352��6+   $��0��68   $��0,56,0,0q�6B   $	��Top=�5      $
��Left=�6+   
 �� View
 5      �� 5      .P�5      ��  �� Width$	 220��6%   $
��Nume��61    �� CellTemplate 5      �� 5      .R�5   "   ��  �� Orientation$ Vertical=�6-   .+�5   &   �� $��20(�60   $��Normal$�6>   ��5    8   ��  �� Path$ Name.FilePath��� 5!   &   $��10(�60   $
��Wrap=�5"   8     ASTIC_client_V2.Attached   FormattedText 5#   0   �� $ Name.Preview�5$   #   5%      5'      5(      5*      �� 5.   
   -	   5        $	 
lb_results��5.      $
��Left=�5/      $��353.2��6.   $��309.6,55.2,0,0q�50      $	��Top=�6+   $��484.6��6C   
 53      �� 54      .P�55      �� $	 240��6%   $
��Nume��61    56      �� 57      .R�58   "   �� $ Vertical=�6-   .+�59   &   �� $��20(�60   $��Normal$�6>   ��5:   8   �� $ Name.FilePath��� 5;   &   $��10(�60   $
��Wrap=�5<   8    5=   0   �� $ Name.Preview�5>   #   5?      5A      �� 5B      $ 40��6%   $	��Tip��60    �� DisplayMemberBinding 6=   �� *FileTypeName+�� 5C      $ 80��6%   $��
Dimensiune��60    6D   �� *Size+�� 5D      $	 120��6%   $
��Data��61    6?   �� *Date+5F      5I      �� 5J   
   
���   �$��1q�6%   $
��Left=�69   $��28��6T   $��0,408.4,0,0q�6`   $	��Top=�6u   $��309.6��6�   ._�5K      �� -
   5         ��Name$ toolStripStatusLabel1��5K      $��Incepeti sa cautati��63   $��0,0,-1,0q�6Q   5L      � 5M   
   -   5         �Name$ toolStripProgressBar1��5M      $
��Left=�65   $��28��5N      $��309.6,408.4,0,0q�6"   $	��Top=�6;   $��484.6��6S    � IsIndeterminate .  5O      �� 5Q   
   
���   �$��1q�6%   $
��Left=�69   $��55��6T   $	��Top=�6`   $	��794��6x   $��-2,0,0,0q�6�   ._�5R      �� -   5         ��Name$ lb_predictions��5R      $��113.2,41.2,253.8,-80.4q�6.   $y�Hidden=�6N   6c   5S      �� 5T   
   -   5        $ cb_view_type��5T      $
��Left=�6)   $��
675,10,0,0q�5U      $	��Top=�6'   $	��111��6?   $��34��6K   $��16(�6W   $
��Bold"�6e   5V      5X      5Y      