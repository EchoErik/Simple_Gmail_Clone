package com.example.simplegmailclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplegmailclone.ui.theme.SimpleGmailCloneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleGmailCloneTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GmailApp()
                }
            }
        }
    }
}

@Composable
fun GmailApp() {
    var emails by remember { mutableStateOf(EmailFetcher.getEmails().take(10)) }
    val totalEmails = EmailFetcher.totalEmails

    LazyColumn(
        modifier = Modifier.fillMaxHeight(0.9f)
    ) {
        items(emails) { email ->
            EmailItem(email = email)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Button(
            onClick = {
                // Load more emails
                val newEmails = EmailFetcher.getNext5Emails()
                emails = (emails + newEmails).take(totalEmails)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Load More")
        }
    }
}


@Composable
fun EmailItem(email: Email) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(IntrinsicSize.Min)
    ) {
        Text(
            text = email.sender,
            fontSize = 16.sp,
            fontWeight = if (email.isUnread) FontWeight.Bold else FontWeight.Normal,
            style = TextStyle(fontStyle = FontStyle.Italic)
        )
        Text(
            text = email.title,
            fontSize = 18.sp,
            fontWeight = if (email.isUnread) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            text = email.summary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GmailAppPreview() {
    val emails = remember { EmailFetcher.getEmails() }

    LazyColumn {
        items(emails) { email ->
            EmailItem(email = email)
        }
    }
}


