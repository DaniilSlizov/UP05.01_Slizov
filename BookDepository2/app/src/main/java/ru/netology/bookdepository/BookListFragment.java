package ru.netology.bookdepository;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import ru.netology.bookdepository.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookListFragment extends Fragment {
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
    private RecyclerView mBookRecyclerView;
    private BookAdapter mAdapter;
    private boolean mSubtitleVisible;
    private class BookHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Book mBook;
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mReadedCheckBox;

        public BookHolder(View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.list_item_book_title_text_view);
            mDateTextView = itemView.findViewById(R.id.list_item_book_date_text_view);
            mReadedCheckBox = itemView.findViewById(R.id.list_item_book_readed_check_box);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = BookPagerActivity.newIntent(getActivity(),
                    mBook.getId());
            startActivity(intent);
        }

        public void bindBook(Book book) {
            mBook = book;
            mTitleTextView.setText(mBook.getTitle());
            mDateTextView.setText(mBook.getDate().toString());
            mReadedCheckBox.setChecked(mBook.isReaded());
        }
    }

    private class BookAdapter extends RecyclerView.Adapter<BookHolder> {
        private List<Book> mBooks;

        public BookAdapter(List<Book> books) {
            mBooks = books;
        }

        @Override
        public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_book, parent, false);
            return new BookHolder(view);
        }

        @Override
        public void onBindViewHolder(BookHolder holder, int position) {
            Book book = mBooks.get(position);
            holder.bindBook(book);
        }

        @Override
        public int getItemCount() {
            return mBooks.size();
        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        mBookRecyclerView = view.findViewById(R.id.book_recycler_view);
        mBookRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (savedInstanceState != null){
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        updateUI();
        return view;
    }
    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_book_list, menu);
        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible){
//            subtitleItem.setTitle(R.string.hide_subtitle);
//        }else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_item_new_book) {
            Book book = new Book();
            BookLab.getBookLab(getActivity()).addBook(book);
            Intent intent = BookPagerActivity.newIntent(getActivity(), book.getId());
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_item_show_subtitle) {
            mSubtitleVisible = !mSubtitleVisible;
            getActivity().invalidateOptionsMenu();
            updateSubtitle();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }



    private void updateSubtitle() {
        BookLab bookLab = BookLab.getBookLab(getActivity());
        int bookCount = bookLab.getBooks().size();
        String subtitle = getResources().getQuantityString(R.plurals.subtitle_plural, bookCount, bookCount);

        if (!mSubtitleVisible) {
            subtitle = null; // Убираем подзаголовок, если он не виден
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setSubtitle(subtitle); // Устанавливаем новый подзаголовок
        }
    }

    private void updateUI() {
        BookLab bookLab = BookLab.getBookLab(getActivity());
        List<Book> books = bookLab.getBooks();
        if (mAdapter == null){
            mAdapter = new BookAdapter(books);
            mBookRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.notifyDataSetChanged();
        }
        updateSubtitle();
    }

}